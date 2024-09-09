package com.busanit501lsy.springcafereservationsample.entity;

import com.busanit501lsy.springcafereservationsample.dto.PaymentDTO;
import com.busanit501lsy.springcafereservationsample.dto.PaymentDTO2;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private LocalDateTime paymentTime;
    private String paymentStatus;
    private String paymentType;

//    @ManyToOne
//    @JoinColumn(name = "reservation_id", nullable = false)
//    @JsonBackReference
//    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "reservation_item_id")
    private ReservationItem reservationItem;

    // DTO를 엔티티로 변환하는 메서드
    public static Payment fromDTO(PaymentDTO2 paymentDTO, ReservationItem reservationItem) {
        Payment payment = new Payment();
        payment.setReservationItem(reservationItem);
        payment.setPrice(new BigDecimal(paymentDTO.getPrice()));
        payment.setPaymentTime(paymentDTO.getPaymentTime());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setPaymentType(paymentDTO.getPaymentType());
        return payment;
    }



}