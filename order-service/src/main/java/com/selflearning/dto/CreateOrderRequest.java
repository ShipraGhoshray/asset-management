package com.selflearning.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(String symbol, BigDecimal quantity, String side, BigDecimal price) {}
