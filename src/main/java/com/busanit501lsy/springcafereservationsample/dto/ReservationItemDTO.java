package com.busanit501lsy.springcafereservationsample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationItemDTO {

    private LocalDate reservationDate;
    private String reservationTime;
    private String username;
    private String phone;
    private String address;
    private String name;
    private BigDecimal price;
    private String description;
    private String itemRepImageId;
    private String itemAdd1ImageId;
    private String itemAdd2ImageId;
    private String itemAdd3ImageId;
    private String itemAdd4ImageId;
}
