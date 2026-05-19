package com.selflearning.messaging.events;

import com.selflearning.messaging.EventType;

public record ReportCommand(
        String orderId,
        String tradeId,
        EventType eventType
) {}
