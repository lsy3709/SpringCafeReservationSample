//package com.busanit501lsy.springcafereservationsample.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class OrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String productName;
//    private int quantity;
//    private BigDecimal price;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    @JsonBackReference
//    private Order order;
//
//
//}