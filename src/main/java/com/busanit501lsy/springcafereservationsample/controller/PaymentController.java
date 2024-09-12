package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Payment;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.service.PaymentService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
@Log4j2
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllPayments(@AuthenticationPrincipal UserDetails user, Model model
            , @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Payment> payments = paymentService.getAllPaymentsWithPage(pageable);

        int startPage = Math.max(0, payments.getNumber() - 5);
        int endPage = Math.min(payments.getTotalPages() - 1, payments.getNumber() + 4);
        model.addAttribute("payments", payments.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageNumber", payments.getNumber());
        model.addAttribute("totalPages", payments.getTotalPages());
        model.addAttribute("pageSize", payments.getSize());

        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            model.addAttribute("user2", user2);
            model.addAttribute("user2_id", user2.getId());
        }
        model.addAttribute("user", user);
        return "payment/payments";
        // returns payments.html
    }

    @GetMapping("/new")
    public String showCreatePaymentForm(@AuthenticationPrincipal UserDetails user, Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        Optional<User> user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.isPresent()) {
            User user2 = user1.get();
            model.addAttribute("user2", user2);
            model.addAttribute("user2_id", user2.getId());
        }
        model.addAttribute("payment", new Payment());
        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("user", user);
        // Provide reservations list to populate dropdown
        return "payment/create-pay";
        // returns create-payments.html
    }

    @PostMapping
    public String createPayment(@ModelAttribute Payment payment) {
        log.info("Payment created : " + payment);
        paymentService.createPayment(payment);
        return "redirect:/payments";
        // Redirect to the list of payments
    }

    @GetMapping("/{id}/edit")
    public String showUpdatePaymentForm(@PathVariable Long id, Model model) {
        paymentService.getPaymentById(id).ifPresent(payment -> {
            model.addAttribute("payment", payment);
            model.addAttribute("reservations", reservationService.getAllReservations());
            // Provide reservations list to populate dropdown
        });
        return "payment/update-pay";
        // returns update-payments.html
    }

    @PostMapping("/{id}")
    public String updatePayment(@PathVariable Long id, @ModelAttribute Payment paymentDetails) {
        paymentService.updatePayment(id, paymentDetails);
        return "redirect:/payments";
        // Redirect to the list of payments
    }

    @GetMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payments";  // Redirect to the list of payments
    }


}
