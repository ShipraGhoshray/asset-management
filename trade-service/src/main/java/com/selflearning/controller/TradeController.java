package com.selflearning.controller;

import com.selflearning.client.OrderServiceClient;
import com.selflearning.dto.ExecutionRequest;
import com.selflearning.dto.OrderStatusResponse;
import com.selflearning.messaging.OrderStatus;
import com.selflearning.service.TradeService;
import com.selflearning.service.TradeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade(@RequestBody ExecutionRequest request) {
        OrderStatus status = tradeService.executeTrade(request);
        if (status == OrderStatus.FILLED) {
            return ResponseEntity.badRequest().body("Order already executed. Cannot execute again.");
        }else{
            return ResponseEntity.ok("Trade execution completed");
        }
    }
}
