package com.selflearning.feigns;

import com.selflearning.model.PricingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "pricing-service",
        url = "${pricing.service.url}"
)
public interface PricingFeignClient {

    @GetMapping("/pricing/value/{id}")
    PricingResponse getPrice(
            @PathVariable("id") String assetId,
            @RequestParam("assetType") String assetType,
            @RequestParam("currency") String currency
    );
}