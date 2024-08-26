package com.busanit501lsy.springcafereservationsample.repository.mongoRepository;

import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ProfileImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends MongoRepository<ProfileImage, String> {
}
