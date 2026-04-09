package com.selflearning.service.impl;

import com.selflearning.service.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class PricingServiceImpl implements PricingService {

    private static PricingServiceImpl pricingService;
    private PricingServiceImpl() {

    }

    public static synchronized PricingServiceImpl getInstance() {
        if (pricingService == null) {
            pricingService = new PricingServiceImpl();
        }
        return pricingService;
    }

    @Override
    public BigDecimal calculateMarketValue(String assetType, String currency) {
        // Initialize pricing models, connect to market data feeds
        return new BigDecimal("250000");
    }
}