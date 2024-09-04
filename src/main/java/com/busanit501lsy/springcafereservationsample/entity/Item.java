package com.busanit501lsy.springcafereservationsample.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    // 웹에서, 관리자, 상품 등록, 수정, 삭제 가능.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    // 대표사진
    // 프로필 이미지, 몽고디비에 업로드
    @Column(name = "item_rep_image_id")
    private String itemRepImageId;

    // 추가사진1
    @Column(name = "item_add1_image_id")
    private String itemAdd1ImageId;

    // 추가사진2
    @Column(name = "item_add2_image_id")
    private String itemAdd2ImageId;

    // 추가사진3
    @Column(name = "item_add3_image_id")
    private String itemAdd3ImageId;

    // 추가사진4
    @Column(name = "item_add4_image_id")
    private String itemAdd4ImageId;

    // ItemImage와 일대다 관계 설정
//    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<ItemImage> images;




    // Other potential fields, like stock quantity, category, etc.
}
