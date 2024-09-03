package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservation-items")
public class ReservationItemController {

    @Autowired
    private ReservationItemService reservationItemService;

    @GetMapping
    public String getAllReservationItems(Model model) {
        List<ReservationItem> reservationItems = reservationItemService.getAllReservationItems();
        model.addAttribute("reservationItems", reservationItems);
        return "reservationItems/list"; // Assuming a Thymeleaf template at src/main/resources/templates/reservationItems/list.html
    }

    @GetMapping("/{id}")
    public String getReservationItemById(@PathVariable Long id, Model model) {
        Optional<ReservationItem> reservationItem = reservationItemService.getReservationItemById(id);
        if (reservationItem.isPresent()) {
            model.addAttribute("reservationItem", reservationItem.get());
            return "reservationItems/detail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/create")
    public String createReservationItemForm(Model model) {
        model.addAttribute("reservationItem", new ReservationItem());
        return "reservationItems/create";
    }

    @PostMapping("/create")
    public String createReservationItem(@ModelAttribute ReservationItem reservationItem) {
        reservationItemService.createReservationItem(reservationItem);
        return "redirect:/reservation-items";
    }

    @GetMapping("/edit/{id}")
    public String updateReservationItemForm(@PathVariable Long id, Model model) {
        Optional<ReservationItem> reservationItem = reservationItemService.getReservationItemById(id);
        if (reservationItem.isPresent()) {
            model.addAttribute("reservationItem", reservationItem.get());
            return "reservationItems/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateReservationItem(@PathVariable Long id, @ModelAttribute ReservationItem reservationItemDetails) {
        reservationItemService.updateReservationItem(id, reservationItemDetails);
        return "redirect:/reservation-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservationItem(@PathVariable Long id) {
        reservationItemService.deleteReservationItem(id);
        return "redirect:/reservation-items";
    }
}