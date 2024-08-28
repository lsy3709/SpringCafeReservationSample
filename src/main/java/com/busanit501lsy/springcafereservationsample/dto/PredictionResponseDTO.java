package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Data;

import java.util.List;

@Data
public class PredictionResponseDTO {
    private int predictedClassIndex;    // 예측된 클래스 인덱스
    private String predictedClassLabel; // 예측된 클래스 레이블
    private double confidence;          // 예측에 대한 신뢰도
    private List<Double> classConfidences; // 각 클래스에 대한 확률
}
