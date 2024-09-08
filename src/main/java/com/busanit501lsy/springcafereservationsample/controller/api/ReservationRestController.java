package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@Log4j2
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationItemService reservationItemService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 유저 아이디, 상품 아이디,예약 생성
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDto) {
        log.info("lsy reservation : " + reservationDto);
        Reservation createdReservation = reservationService.createApiReservation(reservationDto);
        ReservationItem reservationItem = reservationItemService.createApiReservationItem(reservationDto,createdReservation);
        return ResponseEntity.ok(createdReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("reservation : " + id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
