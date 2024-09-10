package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.service.ImageUploadService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/imageAI")
@Log4j2
public class ImageAiController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllItems(@AuthenticationPrincipal UserDetails user, Model model) {
        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            model.addAttribute("user2", user2);
            model.addAttribute("user2_id", user2.getId());
            log.info("User found: " + user2.getId());
            log.info("User found: " + user2.getUsername());
        }
        model.addAttribute("user", user);
        return "aiImage/aiTest"; // Thymeleaf 템플릿
    }



}
