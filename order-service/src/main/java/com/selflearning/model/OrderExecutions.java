package com.selflearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderExecutions {
    @Id
    private String id; // tradeId
    private String orderId;
    private BigDecimal executedQuantity;
    private BigDecimal executedPrice;
    private LocalDateTime executionTime;
}