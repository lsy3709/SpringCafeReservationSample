package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {

    private String username;
    private String password;
    private String email;
    private String profileImageId;
    private String name;
    private String phone;
    private String address;

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
}