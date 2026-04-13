package com.selflearning.service.impl;

import com.selflearning.service.PricingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PricingServiceImpl implements PricingService {

    /*private static PricingServiceImpl pricingService;
    private PricingServiceImpl() { }
    public static synchronized PricingServiceImpl getInstance() {
        if (pricingService == null) {
            pricingService = new PricingServiceImpl();
        }
        return pricingService;
    }*/
    @Override
    public double calculatePrice(String assetId) {
        // Example: fetch asset price from DB or API
        double price = Math.random() * 2000; // stubbed value
        log.info("Calculated price for asset " + assetId + ": " + price);
        return price;
    }
}