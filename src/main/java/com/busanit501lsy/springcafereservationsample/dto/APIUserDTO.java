package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class APIUserDTO extends User implements OAuth2User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String profileImageId;
    private String name;
    private String phone;
    private String address;
    private boolean social;
    //소셜 로그인 정보
    private Map<String, Object> props;
    // 소셜 프로필 이미지 만 뽑기
    private String profileImageServer;

    public APIUserDTO(Long id , String username, String password, String email, String profileImageId,String name, String phone, String address, Collection<GrantedAuthority> authorities,boolean social) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.profileImageId = profileImageId;
        this.social = social;
    }

    public APIUserDTO(String username, String password, String email, String profileImageId,String name, String phone, String address, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.profileImageId = profileImageId;
    }

    //카카오 인증 추가 후 , 생성자 수정
    public APIUserDTO(String username, String password, String email,
                      String profileImageId,String name, String phone,
                      String address, Collection<GrantedAuthority> authorities,
                      String profileImageServer, boolean social) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.profileImageId = profileImageId;
        this.profileImageServer = profileImageServer;
        this.social = social;
    }

    // 카카오 인증 연동시 , 필수 재정의 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

}