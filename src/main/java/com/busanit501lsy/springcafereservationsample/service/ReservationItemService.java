package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.repository.ReservationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationItemService {

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    public List<ReservationItem> getAllReservationItems() {
        return reservationItemRepository.findAll();
    }

    public Optional<ReservationItem> getReservationItemById(Long id) {
        return reservationItemRepository.findById(id);
    }

    public ReservationItem createReservationItem(ReservationItem reservationItem) {
        return reservationItemRepository.save(reservationItem);
    }

    public ReservationItem updateReservationItem(Long id, ReservationItem reservationItemDetails) {
        return reservationItemRepository.findById(id).map(reservationItem -> {
            reservationItem.setNumberOfGuests(reservationItemDetails.getNumberOfGuests());
            // Update other necessary fields
            return reservationItemRepository.save(reservationItem);
        }).orElseThrow(() -> new RuntimeException("ReservationItem not found"));
    }

    public void deleteReservationItem(Long id) {
        reservationItemRepository.deleteById(id);
    }
}
