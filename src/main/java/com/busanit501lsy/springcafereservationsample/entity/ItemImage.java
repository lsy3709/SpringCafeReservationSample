package com.busanit501lsy.springcafereservationsample.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonBackReference
    private Item item;  // Item과 다대일 관계 설정

    // 상품 이미지, 몽고디비에 업로드
    // 오브젝트 아이디
    @Column(name = "item_image_id")
    private String itemImageId;

    // Getter, Setter, Constructor, etc.
}