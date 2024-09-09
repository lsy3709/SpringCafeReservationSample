package com.busanit501lsy.springcafereservationsample.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSlotAvaiableDTO {
    private String itemId;
    private String date;
}
