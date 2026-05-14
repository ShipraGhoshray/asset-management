package com.selflearning.messaging.events;

import java.math.BigDecimal;

public record ExecuteTradeEvent(
        String orderId,
        String symbol,
        BigDecimal quantity,
        BigDecimal price
) {}