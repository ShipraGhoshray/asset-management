package com.selflearning.messaging.events;

import com.selflearning.messaging.EventType;
import com.selflearning.messaging.ReportStatus;

public record ReportEvent(
        String orderId,
        String tradeId,
        EventType eventType,
        ReportStatus status,
        String reason
) {}
