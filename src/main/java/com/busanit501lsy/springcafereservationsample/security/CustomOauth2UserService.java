package com.busanit501lsy.springcafereservationsample.security;

import com.busanit501lsy.springcafereservationsample.dto.APIUserDTO;
import com.busanit501lsy.springcafereservationsample.entity.MemberRole;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 카카오 소셜 로그인시 , 로그인 로직 처리를 여기서 함.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest, 카카오 로그인 관련 정보가 들어가 있다.
        log.info("CustomOauth2UserService : userRequest = " + userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("CustomOauth2UserService : clientRegistration : clientName = " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        paramMap.forEach((k, v) -> {
            log.info("CustomOauth2UserService : k = " + k + " v = " + v);
        });

        String email = null;
        String profileUrlThumbnail = null;

        switch (clientName) {
            case "kakao":
                // 소셜 로그인 정보에서, 이메일만 추출.
                email = getKakaoEmail(paramMap);
                // 소셜 로그인 정보에서, 프로필 이미지 외부 미디어 서버 주소 추출.
                profileUrlThumbnail = getKakaoProfile(paramMap);
                break;
        }

        log.info("CustomOauth2UserService : email = " + email);


        return generateDTO(email,profileUrlThumbnail, paramMap);
    }

    private APIUserDTO generateDTO(String email, String profile_img , Map<String, Object> paramMap) {
        log.info("lsy generateDTO : email = " + email + " profile_img = " + profile_img);
        Optional<User> result = memberRepository.findByEmail(email);
        log.info("lsy result :  = " + result );
        //디비에 유저가 없다면 , 소셜로그인. (이메일포함)
        // 일반 로그인으로 로그인시 (가입한 이메일)
        if (result.isEmpty()) {
            // 회원 추가 하기, mid: 이메일, 패스워드 : 임시로 무조건 1111 , 로하기.
            User member = User.builder()
                    .username(email)
                    .password(passwordEncoder.encode("1111"))
                    .email(email)
                    .social(true)
                    .profileImageServer(profile_img)
                    .build();
            //권한, 일반 USER
            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            // entitty -> DTO
            APIUserDTO memberSecurityDTO =
                    new APIUserDTO(email, "1111", email,null,null,null,null, Arrays.asList(
                            new SimpleGrantedAuthority("ROLE_USER")
                    ),profile_img,true);
            memberSecurityDTO.setProps(paramMap);
            return memberSecurityDTO;
        } // 소셜 로그인 한 정보의 이메일이 디비에 없을 경우
        // 직접 로그인한 정보가 있다, 디비에 소셜 로그인한 이메일이 존재 한다면
        else {
            User member = result.get();
            log.info("lsy generateDTO else " +member );
            log.info("lsy generateDTO else2 " +member.getRoleSet());
            APIUserDTO memberSecurityDTO =
                    new APIUserDTO(
                            member.getUsername(),
                            member.getPassword(),
                            member.getEmail(),
                            member.getProfileImageId(),
                            member.getName(),
                            member.getPhone(),
                            member.getAddress(),
                            member.getRoleSet().stream().map(
                                    memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name())

                            ).collect(Collectors.toList()),
                            member.getProfileImageServer(),
                            member.isSocial()
                    );
            log.info("lsy generateDTO else3 " +memberSecurityDTO );
            return memberSecurityDTO;
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("CustomOauth2UserService : kakao = ");

        Object value = paramMap.get("kakao_account");
        log.info("CustomOauth2UserService : kakao_account = " + value);

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String) accountMap.get("email");
        log.info("CustomOauth2UserService : email = " + email);
        return email;
    }
    // paramMap : 소셜 로그인 정보가 다 들어가 있음.
    private String getKakaoProfile(Map<String, Object> paramMap) {
        log.info("CustomOauth2UserService : kakao = ");

        Object value = paramMap.get("properties");
        log.info("CustomOauth2UserService : properties = " + value);

        LinkedHashMap propertiesMap = (LinkedHashMap) value;

        String thumbnail_image = (String) propertiesMap.get("thumbnail_image");
        log.info("CustomOauth2UserService : thumbnail_image = " + thumbnail_image);
        return thumbnail_image;
    }

}