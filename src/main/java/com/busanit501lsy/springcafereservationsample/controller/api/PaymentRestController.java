package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.dto.PaymentDTO;
import com.busanit501lsy.springcafereservationsample.dto.PaymentDTO2;
import com.busanit501lsy.springcafereservationsample.entity.BuyerInfo;
import com.busanit501lsy.springcafereservationsample.entity.Payment;
import com.busanit501lsy.springcafereservationsample.entity.PrePaymentEntity;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import com.busanit501lsy.springcafereservationsample.service.PaymentService;
import com.busanit501lsy.springcafereservationsample.service.ReservationItemService;
import com.busanit501lsy.springcafereservationsample.service.ReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@Log4j2
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationItemService reservationItemService;


    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO2 paymentDTO) {
        log.info("Payment created" + paymentDTO);
        ReservationItem reservationItem = reservationItemService.getReservationItemById(Long.valueOf(paymentDTO.getReservationItem())).get();
        reservationItem.setPayStatus("결제완료");
        Payment createdPayment = paymentService.createPayment2(paymentDTO,reservationItem);
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // 결제 사전 검증
    @PostMapping("/prepare")
    public void preparePayment(@RequestBody PrePaymentEntity request)
            throws IamportResponseException, IOException {
//        paymentService.postPrepare(request);
        paymentService.postPrepare(request);
    }

    // 결제 사후 검증

    @PostMapping("/validate")
    public ResponseEntity<com.siot.IamportRestClient.response.Payment> validatePayment(@RequestBody PaymentDTO request)
            throws IamportResponseException, IOException {
        com.siot.IamportRestClient.response.Payment payment = paymentService.validatePayment(request);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }


}