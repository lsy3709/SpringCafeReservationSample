package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // 특정 날짜 및 예약 상품에 대해 예약된 시간 슬롯 조회
    @Query("SELECT ts.slotTime FROM TimeSlot ts WHERE ts.reservationItem.item.id = :itemId AND ts.reservation.reservationDate = :reservationDate")
    List<Integer> findReservedTimeSlotsByItemIdAndDate(@Param("itemId") Long itemId, @Param("reservationDate") LocalDate reservationDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM TimeSlot ts WHERE ts.reservationItem.id = :reservationItemId")
    void deleteByReservationItemId(Long reservationItemId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TimeSlot ts WHERE ts.reservation.id = :reservationId")
    void deleteByReservationId(Long reservationId);

}