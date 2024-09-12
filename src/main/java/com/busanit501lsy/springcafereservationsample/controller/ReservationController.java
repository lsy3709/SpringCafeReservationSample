package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Reservation;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
@Log4j2
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllReservations(@AuthenticationPrincipal UserDetails user, Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            model.addAttribute("user2", user2);
        }
        model.addAttribute("reservations", reservations);
        model.addAttribute("user", user);
        return "reservation/reservations";
        // returns reservations.html
    }

    @GetMapping("/new")
    public String showCreateReservationForm(@AuthenticationPrincipal UserDetails user, Model model) {
        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        log.info("lsy reservation form : " + user.getUsername());

        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            log.info("lsy reservation user2 : " + user2.getUsername());
            model.addAttribute("user2", user2);
            model.addAttribute("user2_id", user2.getId());
        }
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
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