package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO;
import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.repository.ItemRepository;
import com.busanit501lsy.springcafereservationsample.repository.ReservationItemRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ReservationItem> getAllReservationItemsWithPage(Pageable pageable) {
        return reservationItemRepository.findAll(pageable);
    }

    // 예약 번호에 대한 하나 조회
    public Page<ReservationItemDTO> getAllReservationItemsWithPageDTO(Long reservationId,Pageable pageable) {
        return reservationItemRepository.findReservationItemsWithDetails(reservationId, pageable);
    }

    // 전체 조회
    public Page<ReservationItemDTO> getAllReservationItemsWithPageDTO2(Pageable pageable) {
        return reservationItemRepository.findReservationItemsWithDetails2(pageable);
    }

    public Optional<ReservationItem> getReservationItemById(Long id) {
        return reservationItemRepository.findById(id);
    }

    @Transactional
    public ReservationItem createReservationItem(ReservationItem reservationItem) {
        return reservationItemRepository.save(reservationItem);
    }

    @Transactional
    public ReservationItem createApiReservationItem(ReservationDTO reservationDto, Reservation reservation) {
        Optional<Item> item = itemRepository.findByName(reservationDto.getSelectedItemName());
        Item item1 = item.orElseThrow(() -> new IllegalArgumentException("Item not found with name: " + reservationDto.getSelectedItemName()));
        log.info("item : " + item1.getName());
        log.info("reservationDto : " + reservationDto.getReservationName());
        log.info("reservation : " + reservation.getReservationTime());
        ReservationItem reservationItem = ReservationItem.builder()
                .reservation(reservation)
                .item(item1)
                .payStatus("입금대기")
                .numberOfGuests(reservationDto.getReservationCount())
                .build();
        return reservationItemRepository.save(reservationItem);
    }

    @Transactional
    public ReservationItem updateReservationItem(Long id, ReservationItem reservationItemDetails) {
        return reservationItemRepository.findById(id).map(reservationItem -> {
            reservationItem.setNumberOfGuests(reservationItemDetails.getNumberOfGuests());
            // Update other necessary fields
            return reservationItemRepository.save(reservationItem);
        }).orElseThrow(() -> new RuntimeException("ReservationItem not found"));
    }

    @Transactional
    public void deleteReservationItem(Long id) {
        reservationItemRepository.deleteById(id);
    }
    @Transactional
    // 특정 reservation id로 관련된 ReservationItem 삭제하는 메서드
    public void deleteReservationItemsByReservationId(Long reservationId) {
        reservationItemRepository.deleteByReservationId(reservationId);
        System.out.println("Deleted reservation items for reservation ID: " + reservationId);
    }
}
