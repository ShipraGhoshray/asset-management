package com.selflearning.dto;

import com.selflearning.messaging.OrderStatus;

public record OrderStatusResponse(
        String orderId,
        OrderStatus status
) {}
