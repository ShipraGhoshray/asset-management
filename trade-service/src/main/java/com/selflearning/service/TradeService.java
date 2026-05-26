package com.selflearning.service;

import com.selflearning.dto.ExecutionRequest;
import com.selflearning.messaging.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public interface TradeService {
    public OrderStatus executeTrade(ExecutionRequest request) ;
}
