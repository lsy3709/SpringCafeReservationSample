package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation-items")
@Log4j2
public class ReservationItemRestController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationItemService reservationItemService;

    @GetMapping
    public List<ReservationItem> getAllReservationItems() {
        return reservationItemService.getAllReservationItems();
    }

    //하나 조회
    @GetMapping("/{reservationId}/page")
    public Page<ReservationItemDTO> getAllItemsWithPage(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @PathVariable Long reservationId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return reservationItemService.getAllReservationItemsWithPageDTO(reservationId,pageable);
    }

    // 전체 조회
    @GetMapping("/page")
    public Page<ReservationItemDTO> getAllItemsWithPage2(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size
                                                        ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return reservationItemService.getAllReservationItemsWithPageDTO2(pageable);
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

    //사용
    @PutMapping("/{id}")
    public ResponseEntity<ReservationItemDTO> updateReservationItem(@PathVariable Long id, @RequestBody ReservationDTO reservationDto) {
        log.info("lsy reservation put: " + reservationDto);
        Reservation createdReservation = reservationService.createApiReservation(reservationDto);
        ReservationItem reservationItem = reservationItemService.createApiReservationItem(reservationDto,createdReservation);
        ReservationItemDTO reservationItemDTO = ReservationItemDTO.fromEntities(createdReservation,reservationItem);
        return ResponseEntity.ok(reservationItemDTO);
    }

    //사용
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationItem(@PathVariable Long id) {
        reservationItemService.deleteReservationItem(id);
        return ResponseEntity.noContent().build();
    }
}