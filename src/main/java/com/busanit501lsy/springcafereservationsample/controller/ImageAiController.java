package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.service.ImageUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imageAI")
@Log4j2
public class ImageAiController {

    @Autowired
    private ImageUploadService imageUploadService;

    @GetMapping
    public String getAllItems(Model model) {
        return "aiImage/aiTest"; // Thymeleaf 템플릿
    }



}
