package com.selflearning.aspect;

import com.selflearning.config.PricingProperties;
import com.selflearning.service.NotificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PricingNotificationAspect {

    private final NotificationService notificationService;
    private final PricingProperties pricingProperties;
    public PricingNotificationAspect(NotificationService notificationService,
                            PricingProperties pricingProperties) {
        this.notificationService = notificationService;
        this.pricingProperties = pricingProperties;
    }
    @AfterReturning(
            pointcut = "execution(* com.selflearning.service.impl.PricingServiceImpl.calculatePrice(..))",
            returning = "price")
    public void notifyIfPriceFalls(JoinPoint joinPoint, Double price) {
        String assetType = (String) joinPoint.getArgs()[0]; // assume first arg is asset type
        Double threshold = pricingProperties.getThresholds().get(assetType);
        if (threshold != null && price < threshold) {
            String assetId = (String) joinPoint.getArgs()[0];
            String message = "ALERT: Price of asset " + assetId + " dropped to " + price;
            notificationService.notifyPriceDrop(message);
        }
    }
}
