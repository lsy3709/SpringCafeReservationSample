package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.PrincipalDetails;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : " + username);
        // 사용자 이름으로 사용자를 조회
        Optional<User> user = userRepository.findByUsername(username);
        User user2 = (User) user.get();
        log.info("username : " + user2.getUsername());
        log.info("username : " + user2.getPassword());
        log.info("username : " + user2.getEmail());
        log.info("username : " + user2.getProfileImageId());


        // UserDetails 객체로 변환
        return new PrincipalDetails(user2);
    }

    // 이 메소드는 리프레시 토큰을 사용할 때, 사용자 ID를 기준으로 UserDetails를 로드할 수 있도록 추가합니다.
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new PrincipalDetails(user);
    }
}

