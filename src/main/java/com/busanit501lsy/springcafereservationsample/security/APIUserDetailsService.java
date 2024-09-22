package com.busanit501lsy.springcafereservationsample.security;

import com.busanit501lsy.springcafereservationsample.dto.APIUserDTO;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {

    //주입
    private final UserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = apiUserRepository.findByUsername(username);

        User apiUser = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find mid"));

        log.info("lsy APIUserDetailsService apiUser-------------------------------------");

        // 일반 유저 로그인과, api 로그인 처리 확인 필요
        APIUserDTO dto =  new APIUserDTO(
                apiUser.getId(),
                apiUser.getUsername(),
                apiUser.getPassword(),
                apiUser.getEmail(),
                apiUser.getProfileImageId(),
                apiUser.getName(),
                apiUser.getPhone(),
                apiUser.getAddress(),
                apiUser.getRoleSet().stream().map(
                        memberRole -> new SimpleGrantedAuthority("ROLE_"+ memberRole.name())
                ).collect(Collectors.toList()),
                apiUser.isSocial());

        log.info("lsy dto : "+dto);

        return dto;
    }
}