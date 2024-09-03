package com.busanit501lsy.springcafereservationsample.controller.api;

import com.busanit501lsy.springcafereservationsample.entity.BuyerInfo;
import com.busanit501lsy.springcafereservationsample.service.BuyerInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BuyerInfoController {

    private final BuyerInfoService buyerInfoService;

    @Autowired
    public BuyerInfoController(BuyerInfoService buyerInfoService) {
        this.buyerInfoService = buyerInfoService;
    }

    @PostMapping("/save_buyerInfo")
    public ResponseEntity<String> saveBuyerInfo(@Valid @RequestBody BuyerInfo buyerInfo) {
        buyerInfoService.saveBuyerInfo(buyerInfo);
        return new ResponseEntity<>("결제정보 저장 완료", HttpStatus.OK);
    }
}
