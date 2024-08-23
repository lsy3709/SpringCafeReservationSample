package com.busanit501lsy.springcafereservationsample.repository;


import com.busanit501lsy.springcafereservationsample.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}