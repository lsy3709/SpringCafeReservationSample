package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDTO2 {
    private String reservationItem ;
    private String price ;
    private LocalDateTime paymentTime;
    private String paymentType;


}