package com.selflearning.service;

import com.selflearning.dto.ExecutionRequest;
import org.springframework.stereotype.Service;

@Service
public interface TradeService {
    public void executeTrade(ExecutionRequest request) ;
}
