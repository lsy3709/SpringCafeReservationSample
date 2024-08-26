package com.busanit501lsy.springcafereservationsample.config;

import com.busanit501lsy.springcafereservationsample.dto.LoginRequestDto;
import com.busanit501lsy.springcafereservationsample.dto.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestDto user = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        JwtUtils jwtUtils1 = new JwtUtils();
        String jwtToken = jwtUtils1.generateAccessToken(principalDetails);

        response.addHeader("Authorization", "Bearer " + jwtToken);

        log.info("successfulAuthentication jwtToken : 내용 : " + jwtToken);

        ObjectMapper om = new ObjectMapper();
        LoginRequestDto cmRequestDto = new LoginRequestDto();
        cmRequestDto.setUsername(principalDetails.getUser().getUsername());
        cmRequestDto.setPassword(principalDetails.getUser().getPassword());

        String cmRequestDtoJson = om.writeValueAsString(cmRequestDto);
        System.out.println("om.writeValueAsString(cmRequestDto); 내용 : " + cmRequestDtoJson);
        PrintWriter out = response.getWriter();
        out.print(cmRequestDtoJson);
        out.flush();
    }
}
