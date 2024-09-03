package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.PrePaymentEntity;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrePaymentRepository extends JpaRepository<PrePaymentEntity, Long> {
    @Query("SELECT p FROM PrePaymentEntity p WHERE p.merchant_uid = :merchantUid")
    Optional<PrePaymentEntity> findByMerchantUid(@Param("merchantUid") String merchantUid);
}
