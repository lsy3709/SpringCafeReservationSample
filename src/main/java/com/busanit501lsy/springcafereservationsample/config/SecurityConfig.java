package com.busanit501lsy.springcafereservationsample.config;

import com.busanit501lsy.springcafereservationsample.security.APIUserDetailsService;
import com.busanit501lsy.springcafereservationsample.security.filter.APILoginFilter;
import com.busanit501lsy.springcafereservationsample.security.filter.RefreshTokenFilter;
import com.busanit501lsy.springcafereservationsample.security.filter.TokenCheckFilter;
import com.busanit501lsy.springcafereservationsample.security.handler.APILoginSuccessHandler;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import com.busanit501lsy.springcafereservationsample.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Log4j2
@Configuration
// 어노테이션을 이용해서, 특정 권한 있는 페이지 접근시, 구분가능.
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// 위 어노테이션 지원중단, 아래 어노테이션 으로 교체, 기본으로 prePostEnabled = true ,
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    //    //주입
    private final APIUserDetailsService apiUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil, APIUserDetailsService
            apiUserDetailsService){
        return new TokenCheckFilter(apiUserDetailsService, jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        log.info("-----------------------configuration---------------------");

        // 1) 로그인 처리하는 겅로 설정과
        // 2) 실제 인증 처리하는 AuthenticationManager설정
        // 세팅1
        // apiLoginFilter 는 /generateToken 경로로 지정
        // 스프링 시큐리티에서 username, password 처리하는 UsernamePasswordAuthenticationFilter 의 앞쪽으로 동작 설정.
        // 확인.
        // 브라우저로 /generateToken 경로 호출시 , 로그로 실행 확인
        //AuthenticationManager설정 세팅1
        // 인증을 위해 UserDetailsService와 PasswordEncoder를 설정한 후,
        // AuthenticationManager를 통해 인증 프로세스를 처리

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(apiUserDetailsService).passwordEncoder(passwordEncoder());
        // Get AuthenticationManager 세팅1
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //반드시 필요 세팅1
        http.authenticationManager(authenticationManager);

        //APILoginFilter 세팅1
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);


        //APILoginSuccessHandler , 세팅2
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil,passwordEncoder());
        //SuccessHandler 세팅2
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        //APILoginFilter의 위치 조정 세팅1, 사용자 인증 전에 ,
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);


        //api로 시작하는 모든 경로는 TokenCheckFilter 동작, 세팅3, 사용자 인증 전에 ,
        http.addFilterBefore(
                tokenCheckFilter(jwtUtil, apiUserDetailsService),
//                tokenCheckFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );

        //refreshToken 호출 처리
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil),
                TokenCheckFilter.class);

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 폼 방식 일경우
        http
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .permitAll()
                )
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/users","/users/new","/refreshToken","/login/oauth2/code/**").permitAll()
                                .requestMatchers("/users/**","/api/users/**").authenticated()

                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/users/new","/refreshToken","/login/oauth2/code/**").permitAll()
                                .requestMatchers("/users/**","/reservations/**","/payments/**").authenticated()


                );

        // 카카오 로그인 API 설정
        http.oauth2Login(
                oauthLogin -> oauthLogin.loginPage("/users/login")
                        .successHandler(authenticationSuccessHandler())
        );

        // 로그 아웃 설정.
        http.logout(
                logout -> logout.logoutUrl("/users/logout").logoutSuccessUrl("/users/login")

        );

        //로그인 후, 성공시 리다이렉트 될 페이지 지정, 간단한 버전.
        http.formLogin(formLogin ->
                formLogin.defaultSuccessUrl("/users",true)
        );




//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/**","/refreshTest.html","/sendJWT.html","/v3/api-docs/**","/swagger-ui/**","/js**","/css/**","/generateToken","/users/*/profileImage","/api/users/*/profileImage","/users","/users/new","/users/login", "/api/token/refresh").permitAll()
//                        .anyRequest().authenticated());
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }

    // 소셜 로그인 후, 후처리 하는 빈등록.
    // 카카오 로그인 API 설정, 수정.
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new APILoginSuccessHandler(jwtUtil,passwordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}