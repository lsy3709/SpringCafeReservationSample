package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.entity.OrderInfo;
import com.busanit501lsy.springcafereservationsample.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;

    @Autowired
    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    @PostMapping("/save_orderInfo")
    public ResponseEntity<String> saveOrderInfo(@RequestBody OrderInfo orderInfo) {
        orderInfoService.saveOrderInfo(orderInfo);
        return new ResponseEntity<>("Order information saved successfully", HttpStatus.OK);
    }
}
