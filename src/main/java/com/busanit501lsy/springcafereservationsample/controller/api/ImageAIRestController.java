package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.PredictionResponseDTO;
import com.busanit501lsy.springcafereservationsample.service.ImageUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
@Log4j2
public class ImageAIRestController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageAIRestController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/predict")
    public PredictionResponseDTO uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        // Django 서버로 이미지 전송 및 응답 처리
        PredictionResponseDTO responseDTO = imageUploadService.sendImageToDjangoServer(image.getBytes(), image.getOriginalFilename());

        // PredictionResponseDTO 객체를 JSON으로 반환
        return responseDTO;
//        return imageUploadService.sendImageToDjangoServer(image.getBytes(), image.getOriginalFilename());
    }


}
