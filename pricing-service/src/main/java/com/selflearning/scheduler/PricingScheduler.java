package com.selflearning.scheduler;

import com.selflearning.config.PricingProperties;
import com.selflearning.service.PricingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PricingScheduler {

    private final PricingService pricingService;
    private final PricingProperties pricingProperties;
    public PricingScheduler(PricingService pricingService,
                            PricingProperties pricingProperties) {
        this.pricingService = pricingService;
        this.pricingProperties = pricingProperties;
    }

    @Scheduled(fixedRateString = "${pricing.scheduler.rate}")
    public void checkPrices() {
        for (String assetType : pricingProperties.getAssets()) {
            pricingService.calculatePrice(assetType);
        }
    }
}