package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {

}
