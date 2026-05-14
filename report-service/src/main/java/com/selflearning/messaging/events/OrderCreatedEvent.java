package com.selflearning.messaging.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        String orderId,
        String symbol,
        BigDecimal quantity,
        BigDecimal price,
        String side,
        String timestamp
) {}
