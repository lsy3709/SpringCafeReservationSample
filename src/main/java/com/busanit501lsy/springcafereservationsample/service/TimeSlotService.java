package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.entity.TimeSlot;
import com.busanit501lsy.springcafereservationsample.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    // Add a new TimeSlot
    @Transactional
    public TimeSlot addTimeSlot(Integer slotTime, Reservation reservation, ReservationItem reservationItem) {
        TimeSlot timeSlot = TimeSlot.builder()
                .slotTime(slotTime)
                .reservation(reservation)
                .reservationItem(reservationItem)
                .build();
        return timeSlotRepository.save(timeSlot);
    }

    // Delete an existing TimeSlot by id
    @Transactional
    public void deleteTimeSlot(Long id) {
        Optional<TimeSlot> timeSlot = timeSlotRepository.findById(id);
        if (timeSlot.isPresent()) {
            timeSlotRepository.delete(timeSlot.get());
        } else {
            throw new IllegalArgumentException("TimeSlot with id " + id + " not found");
        }
    }
    public void deleteTimeSlotsByReservationItemId(Long reservationItemId) {
        timeSlotRepository.deleteByReservationItemId(reservationItemId);
        System.out.println("Deleted time slots for reservation item ID: " + reservationItemId);
    }

    @Transactional
    public void deleteTimeSlotsByReservationId(Long reservationId) {
        timeSlotRepository.deleteByReservationId(reservationId);
    }

}
