package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO;
import com.busanit501lsy.springcafereservationsample.dto.TimeSlotDTO;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.entity.TimeSlot;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.busanit501lsy.springcafereservationsample.service.TimeSlotService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    private TimeSlotService timeSlotService;

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
    // 사용
    @PostMapping
    public ResponseEntity<ReservationItemDTO> createReservation(@RequestBody ReservationDTO reservationDto) {
        log.info("lsy reservation : " + reservationDto);
        Reservation createdReservation = reservationService.createApiReservation(reservationDto);
        ReservationItem reservationItem = reservationItemService.createApiReservationItem(reservationDto,createdReservation);
        ReservationItemDTO reservationItemDTO = ReservationItemDTO.fromEntities(createdReservation,reservationItem);
        return ResponseEntity.ok(reservationItemDTO);
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

    @GetMapping("/available-times")
    public List<Integer> getAvailableTimeSlots(@RequestParam Long itemId, @RequestParam String date) {
        LocalDate reservationDate = LocalDate.parse(date);
        return reservationService.getAvailableTimeSlots(itemId, reservationDate);
    }


    // Add a new TimeSlot
    @PostMapping("/add")
    public ResponseEntity<TimeSlot> addTimeSlot(
            @RequestBody TimeSlotDTO timeSlotDTO) {

        log.info("lsy timeslot reservation : " + timeSlotDTO.getReservationId());
        log.info("lsy timeslot reservationItemId : " + timeSlotDTO.getReservationItemId());
        log.info("lsy timeslot slot time : " + timeSlotDTO.getSlotTime());
        Reservation reservation = reservationService.getReservationById(Long.valueOf(timeSlotDTO.getReservationId())).get() ;
        ReservationItem reservationItem = reservationItemService.getReservationItemById(Long.valueOf(timeSlotDTO.getReservationItemId())).get() ;
        Integer timeSlot = Integer.valueOf(timeSlotDTO.getSlotTime());
        TimeSlot newTimeSlot = timeSlotService.addTimeSlot(timeSlot, reservation, reservationItem);
        return ResponseEntity.ok(newTimeSlot);
    }

    // Delete a TimeSlot by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.ok("TimeSlot deleted successfully");
    }

}
