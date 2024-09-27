package com.busanit501lsy.springcafereservationsample.dto;

import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class ReservationItemDTO {

    private Long reservationId;
    private Long reservationItemId;
    private LocalDate reservationDate;
    private String reservationTime;
    private String username;
    private String phone;
    private String address;
    private String name;
    // 입금 상태 추가하기
    private String payStatus;
    private BigDecimal price;
    private String description;
    private String itemRepImageId;
    private String itemAdd1ImageId;
    private String itemAdd2ImageId;
    private String itemAdd3ImageId;
    private String itemAdd4ImageId;

    // 제외한 필드 2개
    //  private Long reservationId;
    //    private Long reservationItemId;
    // 필드들을 이용한 생성자
    public ReservationItemDTO(LocalDate reservationDate, String reservationTime, String username, String phone,
                              String address, String name, BigDecimal price, String description,
                              String itemRepImageId, String itemAdd1ImageId, String itemAdd2ImageId,
                              String itemAdd3ImageId, String itemAdd4ImageId) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.price = price;
        this.description = description;
        this.itemRepImageId = itemRepImageId;
        this.itemAdd1ImageId = itemAdd1ImageId;
        this.itemAdd2ImageId = itemAdd2ImageId;
        this.itemAdd3ImageId = itemAdd3ImageId;
        this.itemAdd4ImageId = itemAdd4ImageId;
    }

    // 엔티티2개 받아서, 내가 원하는 dto 형태로 반환하기.
    public static ReservationItemDTO fromEntities(Reservation reservation,
                                                  ReservationItem reservationItem) {
        ReservationItemDTO dto = new ReservationItemDTO();

        // Reservation 엔티티에서 가져오는 값
        dto.reservationId = reservation.getId();
        dto.reservationDate = reservation.getReservationDate();
        dto.reservationTime = reservation.getReservationTime();
        dto.username = reservation.getUser().getName();
        dto.phone = reservation.getUser().getPhone();
        dto.address = reservation.getUser().getAddress();


        // ReservationItem 엔티티에서 가져오는 값
        dto.reservationItemId = reservationItem.getId();
        dto.name = reservationItem.getItem().getName();
        dto.price = reservationItem.getItem().getPrice();
        dto.payStatus = reservationItem.getPayStatus();
        dto.description = reservationItem.getItem().getDescription();
        dto.itemRepImageId = reservationItem.getItem().getItemRepImageId();
        dto.itemAdd1ImageId = reservationItem.getItem().getItemAdd1ImageId();
        dto.itemAdd2ImageId = reservationItem.getItem().getItemAdd2ImageId();
        dto.itemAdd3ImageId = reservationItem.getItem().getItemAdd3ImageId();
        dto.itemAdd4ImageId = reservationItem.getItem().getItemAdd4ImageId();

        return dto;
    }

}
