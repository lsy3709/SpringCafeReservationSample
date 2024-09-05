package com.busanit501lsy.springcafereservationsample.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
//@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationTime;
    private int numberOfGuests;

    private String selectedItemName;
    private String selectedItemPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

//    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Payment> payments;

//    @JsonBackReference: 자식 객체에 이 어노테이션을 사용.
//    Jackson은 이 어노테이션이 붙은 필드를 직렬화에서 제외합니다.
//    이를 통해 자식 객체가 부모 객체를 참조하는 순환을 피할 수 있습니다.
}