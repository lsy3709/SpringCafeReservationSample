package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository apiUserRepository;

    @Test
    public void testInserts() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            User apiUser = User.builder()
                    .username("user"+i)
                    .password( passwordEncoder.encode("1111") )
                    .email(("user"+i+"@naver.com"))
                    .build();

            apiUserRepository.save(apiUser);
        });
    }
}
