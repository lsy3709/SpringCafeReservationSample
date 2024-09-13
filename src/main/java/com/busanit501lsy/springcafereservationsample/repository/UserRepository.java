package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roleSet")
    Optional<User> findByUsername(String username);

    // 이름으로 사용자 검색
    @EntityGraph(attributePaths = "roleSet")
    Optional<User> findByName(String name);

    // 이메일으로 유저 확인.
    @EntityGraph(attributePaths = "roleSet")
    Optional<User> findByEmail(String email);

}