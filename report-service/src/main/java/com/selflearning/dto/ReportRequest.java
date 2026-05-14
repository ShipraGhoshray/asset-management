package com.selflearning.dto;

public record ReportRequest(
        String orderId,
        String tradeId
) {}