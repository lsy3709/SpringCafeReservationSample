package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReservationItemRepositoryTests {

    @Autowired
    private ReservationItemRepository reservationItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testInserts() {
        Item item = itemRepository.findById(1L).get();
        Reservation reservation = reservationRepository.findById(1L).get();
        IntStream.rangeClosed(1,100).forEach(i -> {
            ReservationItem reservationItem = ReservationItem.builder()
                    .numberOfGuests(4)
                    .item(item)
                    .reservation(reservation)
                    .build();

            reservationItemRepository.save(reservationItem);
        });
    }
}
