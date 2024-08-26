package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름으로 사용자를 조회
        Optional<Object> user = userRepository.findByUsername(username);
        User user2 = (User) user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // UserDetails 객체로 변환
        return User.create(user2);
    }

    // 이 메소드는 리프레시 토큰을 사용할 때, 사용자 ID를 기준으로 UserDetails를 로드할 수 있도록 추가합니다.
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return User.create(user);
    }
}

