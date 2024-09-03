package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.BuyerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerInfoRepository extends JpaRepository<BuyerInfo, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
}