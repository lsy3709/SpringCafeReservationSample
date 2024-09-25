package com.busanit501lsy.springcafereservationsample.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class KakaoOAuth2Controller {
//login/oauth2/code/kakao?code=test&state=test
    @GetMapping("/custom/oauth2/code/kakao")
    public String handleKakaoRedirect(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            Model model) {

        // 인증 코드와 state 값 처리
        model.addAttribute("code", code);
        model.addAttribute("state", state);
        log.info("lsy code ", "동작확인1");
        log.info("lsy code2 ", code);

        // 여기에 인증 코드 처리 로직을 추가할 수 있습니다. (토큰 요청 등)

        // 인증 후 보여줄 페이지를 리턴
        return "kakao/kakaoCallbackPage"; // 템플릿의 이름 (예: kakaoCallbackPage.html)
    }
}
