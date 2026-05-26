package com.selflearning.messaging.events;

import com.selflearning.messaging.ExecutionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderExecutionEvent(
        String orderId,
        String tradeId,
        String symbol,
        BigDecimal executedQuantity,
        BigDecimal executedPrice,
        String accountId,
        LocalDateTime executionTime,
        ExecutionType executionType
) {}