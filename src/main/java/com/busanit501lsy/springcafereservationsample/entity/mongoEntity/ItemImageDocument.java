package com.busanit501lsy.springcafereservationsample.entity.mongoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "item_image_documents")
public class ItemImageDocument {

    @Id
    private String id;  // MongoDB의 기본 식별자

    // 상품의 아이디
    @Column(nullable = false, unique = true)
    private String itemId;  // RDBMS의 itemImageId 필드와 매핑되는 값

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    private byte[] data;

    // Getter, Setter, Constructor, etc.
}