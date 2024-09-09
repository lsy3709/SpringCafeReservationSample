package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.OrderInfo;
import com.busanit501lsy.springcafereservationsample.repository.OrderInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {

    private final OrderInfoRepository orderInfoRepository;

    @Autowired
    public OrderInfoService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    @Transactional
    public void saveOrderInfo(OrderInfo orderInfo) {
        orderInfoRepository.save(orderInfo);
    }
}

