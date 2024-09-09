package com.busanit501lsy.springcafereservationsample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private String reservationName;       // 예약자 이름 (select)
    private LocalDate reservationDate;
    private Integer reservationCount;     // 예약 인원 (number)
    private String selectedItemName;      // 선택된 상품 이름 (readonly text)
    private String selectedItemPrice;     // 선택된 상품 가격 (readonly text)
    private String reservationTime; // 예약 시간 (time), 매시간마다
    private String payStatus;
}
