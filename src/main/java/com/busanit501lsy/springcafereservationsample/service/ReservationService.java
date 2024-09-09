package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.ReservationRepository;
import com.busanit501lsy.springcafereservationsample.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Log4j2
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserService userService;
    @Autowired
    TimeSlotRepository timeSlotRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // 예약 작업중.
    @Transactional
    public Reservation createApiReservation(ReservationDTO reservationDto) {

        Optional<User> user = userService.getUserByUsername(reservationDto.getReservationName());
        User user1 = user.get();
        log.info("user : " + user1.getName());
        log.info("reservationDto : " + reservationDto);


        Reservation reservation = Reservation.builder()
                .reservationDate(reservationDto.getReservationDate())
                .reservationTime(reservationDto.getReservationTime())
                .user(user1)
                .build();
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setReservationTime(reservationDetails.getReservationTime());
//        reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
        reservation.setUser(reservationDetails.getUser());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservationRepository.delete(reservation);
    }


    // 특정 상품과 날짜에 예약 가능한 시간 슬롯 조회
    public List<Integer> getAvailableTimeSlots(Long itemId, LocalDate reservationDate) {
        // 해당 상품과 날짜에 예약된 시간 슬롯을 조회
        List<Integer> reservedTimeSlots = timeSlotRepository.findReservedTimeSlotsByItemIdAndDate(itemId, reservationDate);

        // 0부터 23까지의 모든 시간 리스트 생성
        List<Integer> allTimeSlots = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList());

        // 예약된 시간을 제외한 나머지 시간 반환
        return allTimeSlots.stream()
                .filter(timeSlot -> !reservedTimeSlots.contains(timeSlot))
                .collect(Collectors.toList());
    }// end 함수

}