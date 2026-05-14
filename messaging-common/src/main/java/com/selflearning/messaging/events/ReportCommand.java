package com.selflearning.messaging.events;

public record ReportCommand(
        String orderId,
        String tradeId,
        String eventType
) {}
