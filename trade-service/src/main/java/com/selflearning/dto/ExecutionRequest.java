package com.selflearning.dto;

import java.math.BigDecimal;
public record ExecutionRequest(
        String orderId,
        String symbol,
        BigDecimal quantity,
        BigDecimal price
) {}