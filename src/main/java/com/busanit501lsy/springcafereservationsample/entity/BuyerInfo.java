package com.busanit501lsy.springcafereservationsample.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "buyer_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_uid", nullable = false, unique = true)
    private String merchantUid;

    @Column(name = "userid", nullable = false)
    private String userId;

    @Column(name = "pay_method", nullable = false)
    private String payMethod;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "addr", nullable = false)
    private String addr;

    @Column(name = "post", nullable = false)
    private String post;

    @Column(name = "recipient", nullable = false)
    private String recipient;
}