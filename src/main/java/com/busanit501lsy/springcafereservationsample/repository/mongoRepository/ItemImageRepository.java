package com.busanit501lsy.springcafereservationsample.repository.mongoRepository;

import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ItemImage;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ProfileImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ItemImageRepository extends MongoRepository<ItemImage, String> {
}
