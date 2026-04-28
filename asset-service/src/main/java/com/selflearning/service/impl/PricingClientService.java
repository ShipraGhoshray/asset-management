package com.selflearning.service.impl;

import com.selflearning.feigns.PricingFeignClient;
import com.selflearning.model.PricingResponse;
import org.springframework.stereotype.Service;

@Service
public class PricingClientService {

    private final PricingFeignClient pricingFeignClient;
    public PricingClientService(PricingFeignClient pricingFeignClient) {
        this.pricingFeignClient = pricingFeignClient;
    }

    public PricingResponse fetchPrice(String assetId, String assetType, String currency) {
        return pricingFeignClient.getPrice(assetId, assetType, currency);
    }
}