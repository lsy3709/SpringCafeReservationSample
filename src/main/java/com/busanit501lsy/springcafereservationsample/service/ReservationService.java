package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.dto.ReservationDTO;
import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.repository.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserService userService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // 예약 작업중.
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

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setReservationTime(reservationDetails.getReservationTime());
//        reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
        reservation.setUser(reservationDetails.getUser());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservationRepository.delete(reservation);
    }
}