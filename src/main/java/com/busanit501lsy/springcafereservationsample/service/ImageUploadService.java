package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.PredictionResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Log4j2
public class ImageUploadService {

    private final WebClient webClient;

    public ImageUploadService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
    }

    public Mono<PredictionResponseDTO> uploadImage(MultipartFile file) throws IOException {
        log.info("ai file.getOriginalFilename() : " + file.getOriginalFilename());
        Resource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        return webClient.post()
                .uri("/api/classify/")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(resource)
                .retrieve()
                .bodyToMono(PredictionResponseDTO.class);
//                .onErrorResume(WebClientResponseException.class, ex -> {
//                    return Mono.just("Error occurred: " + ex.getMessage());
//                });
    }
}
