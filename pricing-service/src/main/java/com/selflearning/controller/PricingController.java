package com.selflearning.controller;

import com.selflearning.model.PricingResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
public class PricingController {

    @GetMapping("/value/{assetId}")
    public PricingResponse getAssetPrice( @PathVariable String assetId,
                                          @RequestParam String assetType,
                                          @RequestParam String currency) {
        double price = 37.8 ;//marketDataService.calculatePrice(assetId);
        return new PricingResponse(assetType, price, currency);
    }
}
