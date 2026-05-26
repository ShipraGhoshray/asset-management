package com.selflearning.service;

import com.selflearning.messaging.events.OrderExecutionEvent;

public interface PortfolioService {
    public void handleOrderExecution(OrderExecutionEvent event);
}
