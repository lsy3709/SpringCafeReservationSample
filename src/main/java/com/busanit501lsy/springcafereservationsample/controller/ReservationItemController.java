package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reservation-items")
public class ReservationItemController {

    @Autowired
    private ReservationItemService reservationItemService;
    @Autowired
    private UserService userService;


    @GetMapping
    public String getAllReservationItems(@AuthenticationPrincipal UserDetails user, Model model,
     @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            model.addAttribute("user2", user2);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ReservationItem> itemPage = reservationItemService.getAllReservationItemsWithPage(pageable);

        int startPage = Math.max(0, itemPage.getNumber() - 5);
        int endPage = Math.min(itemPage.getTotalPages() - 1, itemPage.getNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("pageNumber", itemPage.getNumber());
        model.addAttribute("totalPages", itemPage.getTotalPages());
        model.addAttribute("pageSize", itemPage.getSize());
        model.addAttribute("user", user);
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

    @GetMapping("/{id}/edit")
    public String updateReservationItemForm(@PathVariable Long id, Model model) {
        Optional<ReservationItem> reservationItem = reservationItemService.getReservationItemById(id);
        if (reservationItem.isPresent()) {
            model.addAttribute("reservationItem", reservationItem.get());
            return "reservationItems/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateReservationItem(@PathVariable Long id, @ModelAttribute ReservationItem reservationItemDetails) {
        reservationItemService.updateReservationItem(id, reservationItemDetails);
        return "redirect:/reservation-items";
    }

    @GetMapping("/{id}/delete")
    public String deleteReservationItem(@PathVariable Long id) {
        reservationItemService.deleteReservationItem(id);
        return "redirect:/reservation-items";
    }
}