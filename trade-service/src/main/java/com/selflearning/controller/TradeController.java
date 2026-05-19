package com.selflearning.controller;

import com.selflearning.dto.ExecutionRequest;
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
        tradeService.executeTrade(request);
        return ResponseEntity.ok("Trade execution completed");
    }
}
