package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSlotDTO {
    private String slotTime;
    private String reservationId;
    private String reservationItemId;
}
