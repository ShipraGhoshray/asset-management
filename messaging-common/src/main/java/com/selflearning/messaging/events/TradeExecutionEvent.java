package com.selflearning.messaging.events;

import java.math.BigDecimal;

public record TradeExecutionEvent(String orderId,
                                  String tradeId,
                                  String symbol,
                                  BigDecimal executedQuantity,
                                  BigDecimal executedPrice
) {}