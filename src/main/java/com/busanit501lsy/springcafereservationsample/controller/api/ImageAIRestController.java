package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.PredictionResponseDTO;
import com.busanit501lsy.springcafereservationsample.service.ImageUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<PredictionResponseDTO>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        return imageUploadService.uploadImage(file)
                .map(response -> ResponseEntity.ok().body(response));
    }


}
