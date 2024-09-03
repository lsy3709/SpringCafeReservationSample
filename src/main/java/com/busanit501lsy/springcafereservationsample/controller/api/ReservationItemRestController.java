package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation-items")
public class ReservationItemRestController {
    @Autowired
    private ReservationItemService reservationItemService;

    @GetMapping
    public List<ReservationItem> getAllReservationItems() {
        return reservationItemService.getAllReservationItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationItem> getReservationItemById(@PathVariable Long id) {
        Optional<ReservationItem> reservationItem = reservationItemService.getReservationItemById(id);
        return reservationItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservationItem createReservationItem(@RequestBody ReservationItem reservationItem) {
        return reservationItemService.createReservationItem(reservationItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationItem> updateReservationItem(@PathVariable Long id, @RequestBody ReservationItem reservationItemDetails) {
        return ResponseEntity.ok(reservationItemService.updateReservationItem(id, reservationItemDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationItem(@PathVariable Long id) {
        reservationItemService.deleteReservationItem(id);
        return ResponseEntity.noContent().build();
    }
}