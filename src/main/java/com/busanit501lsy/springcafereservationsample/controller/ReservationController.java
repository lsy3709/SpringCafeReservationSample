package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
@Log4j2
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservation/reservations";
        // returns reservations.html
    }

    @GetMapping("/new")
    public String showCreateReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("users", userService.getAllUsers());
        // Provide users list to populate dropdown
        return "reservation/create-reservation";
        // returns create-reservation.html
    }

    @PostMapping
    public String createReservation(@ModelAttribute Reservation reservation) {
        log.info("reservation : " + reservation);
        reservationService.createReservation(reservation);
        return "redirect:/reservations";
        // Redirect to the list of reservations
    }

    @GetMapping("/{id}/edit")
    public String showUpdateReservationForm(@PathVariable Long id, Model model) {
        reservationService.getReservationById(id).ifPresent(reservation -> {
            model.addAttribute("reservation", reservation);
            model.addAttribute("users", userService.getAllUsers());
            // Provide users list to populate dropdown
        });
        return "reservation/update-reservation";
        // returns update-reservation.html
    }

    @PostMapping("/edit")
    public String updateReservation(@ModelAttribute Reservation reservationDetails) {
        reservationService.updateReservation(reservationDetails.getId(), reservationDetails);
        return "redirect:/reservations";
        // Redirect to the list of reservations
    }

    @GetMapping("/{id}/delete")
    public String deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
        // Redirect to the list of reservations
    }
}