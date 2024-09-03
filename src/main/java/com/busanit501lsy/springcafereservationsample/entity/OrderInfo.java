package com.busanit501lsy.springcafereservationsample.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_uid", nullable = false, unique = true)
    private String merchant_uid;

    @Column(name = "userid", nullable = false)
    private String userId;

    @Column(name = "gcode", nullable = false)
    private String gcode;

    @Column(name = "gname", nullable = false)
    private String gname;

    @Column(name = "gprice")
    private Integer gprice;

    @Column(name = "gimage")
    private String gimage;

    @Column(name = "gcolor")
    private String gcolor;

    @Column(name = "gsize")
    private String gsize;

    @Column(name = "gamount")
    private Integer gamount;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "post", nullable = false)
    private String post;

    @Column(name = "addr", nullable = false)
    private String addr;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "cartid")
    private String cartid;
}
