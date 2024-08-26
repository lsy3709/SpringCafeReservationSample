package com.busanit501lsy.springcafereservationsample.config;

import com.busanit501lsy.springcafereservationsample.service.UserDetailsServiceImpl;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
public class SecurityConfig {

//    private final JWTUtil jwtUtil;
//    //주입
//    private final APIUserDetailsService apiUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil, APIUserDetailsService
//            apiUserDetailsService){
//        return new TokenCheckFilter(apiUserDetailsService, jwtUtil);
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        log.info("-----------------------configuration---------------------");

        //AuthenticationManager설정
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(apiUserDetailsService).passwordEncoder(passwordEncoder());
        // Get AuthenticationManager
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //반드시 필요
//        http.authenticationManager(authenticationManager);

        //APILoginFilter
//        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
//        apiLoginFilter.setAuthenticationManager(authenticationManager);


        //APILoginSuccessHandler
//        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        //SuccessHandler 세팅
//        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        //APILoginFilter의 위치 조정
//        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);


        //api로 시작하는 모든 경로는 TokenCheckFilter 동작
//        http.addFilterBefore(
//                tokenCheckFilter(jwtUtil, apiUserDetailsService),
//                UsernamePasswordAuthenticationFilter.class
//        );

        //refreshToken 호출 처리
//        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil),
//                TokenCheckFilter.class);

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/users/*/profileImage","/users","/users/new","/users/login", "/api/token/refresh").permitAll()
//                        .anyRequest().authenticated());
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}