package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.PredictionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class ImageUploadService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱을 위한 ObjectMapper

    @Transactional
    public PredictionResponseDTO sendImageToDjangoServer(byte[] imageBytes, String filename) throws IOException {
        // 이미지 파일을 MultipartBody로 구성
        RequestBody fileBody = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));

        // Multipart request body
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", filename, fileBody)
                .build();

        // Request 객체 생성
        Request request = new Request.Builder()
                .url("http://localhost:8000/api/classify/")
                .post(requestBody)
                .build();

        // 요청 실행
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 응답 바디를 String으로 읽기
            String responseBody = response.body().string();
            log.info("responseBody : " + responseBody);

            // 응답을 PredictionResponseDTO 객체로 변환
            return objectMapper.readValue(responseBody, PredictionResponseDTO.class);
        }
    }

}
