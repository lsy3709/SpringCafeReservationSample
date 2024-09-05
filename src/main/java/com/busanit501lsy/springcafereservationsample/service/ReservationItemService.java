package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDto;
import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.repository.ItemRepository;
import com.busanit501lsy.springcafereservationsample.repository.ReservationItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReservationItemService {

    @Autowired
    private ReservationItemRepository reservationItemRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<ReservationItem> getAllReservationItems() {
        return reservationItemRepository.findAll();
    }

    public Optional<ReservationItem> getReservationItemById(Long id) {
        return reservationItemRepository.findById(id);
    }

    public ReservationItem createReservationItem(ReservationItem reservationItem) {
        return reservationItemRepository.save(reservationItem);
    }

    public ReservationItem createApiReservationItem(ReservationDto reservationDto, Reservation reservation) {
        Optional<Item> item = itemRepository.findByName(reservationDto.getSelectedItemName());
        Item item1 = item.orElseThrow(() -> new IllegalArgumentException("Item not found with name: " + reservationDto.getSelectedItemName()));
        log.info("item : " + item1.getName());
        log.info("reservationDto : " + reservationDto.getReservationName());
        log.info("reservation : " + reservation.getReservationTime());
        ReservationItem reservationItem = ReservationItem.builder()
                .reservation(reservation)
                .item(item1)
                .numberOfGuests(reservationDto.getReservationCount())
                .build();
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
