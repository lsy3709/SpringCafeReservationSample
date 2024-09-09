package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.BuyerInfo;
import com.busanit501lsy.springcafereservationsample.repository.BuyerInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerInfoService {

    private final BuyerInfoRepository buyerInfoRepository;

    @Autowired
    public BuyerInfoService(BuyerInfoRepository buyerInfoRepository) {
        this.buyerInfoRepository = buyerInfoRepository;
    }

    @Transactional
    public void saveBuyerInfo(BuyerInfo buyerInfo) {
        buyerInfoRepository.save(buyerInfo);
    }
}
