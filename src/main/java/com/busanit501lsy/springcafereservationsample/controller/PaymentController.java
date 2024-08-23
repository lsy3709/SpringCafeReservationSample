package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Payment;
import com.busanit501lsy.springcafereservationsample.service.PaymentService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String getAllPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/payments";
        // returns payments.html
    }

    @GetMapping("/new")
    public String showCreatePaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("reservations", reservationService.getAllReservations());
        // Provide reservations list to populate dropdown
        return "payment/create-pay";
        // returns create-payments.html
    }

    @PostMapping
    public String createPayment(@ModelAttribute Payment payment) {
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
