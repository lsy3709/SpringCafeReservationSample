package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.PredictionResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
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
        log.info("ailsy 2 ImageUploadService file.getOriginalFilename() : " + file.getOriginalFilename());
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new ByteArrayResource(file.getBytes()))
                .header("Content-Disposition", "form-data; name=image; filename=" + file.getOriginalFilename());

        return webClient.post()
                .uri("/api/classify/")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(PredictionResponseDTO.class);
    }
}
