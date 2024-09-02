package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.Payment;
import com.busanit501lsy.springcafereservationsample.entity.PrePaymentEntity;
import com.busanit501lsy.springcafereservationsample.repository.PaymentRepository;
import com.busanit501lsy.springcafereservationsample.repository.PrePaymentRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private IamportClient api;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PrePaymentRepository prePaymentRepository;

    // 포트원
    // 관린자 콘솔 , https://admin.portone.io/
    // 로그인 후, 연동관리 -> 연동정보 -> REST API KEY, REST API SECRET 가져오기,
    public PaymentService() {
//        this.api = new IamportClient("REST API KEY", "REST API SECRET");


    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentTime(paymentDetails.getPaymentTime());
        payment.setReservation(paymentDetails.getReservation());

        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }

    public void postPrepare(PrePaymentEntity request) throws IamportResponseException, IOException {
        PrepareData prepareData = new PrepareData(request.getMerchant_uid(), request.getAmount());
        api.postPrepare(prepareData);  // 사전 등록 API

        prePaymentRepository.save(request); // 주문번호와 결제예정 금액 DB 저장
    }
}
