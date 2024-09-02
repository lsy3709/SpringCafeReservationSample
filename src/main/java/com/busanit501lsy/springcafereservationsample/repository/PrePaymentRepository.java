package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.PrePaymentEntity;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrePaymentRepository extends JpaRepository<PrePaymentEntity, Long> {
}
