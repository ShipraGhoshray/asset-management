package com.selflearning.model;

import com.selflearning.messaging.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, length = 20)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false, length = 10)
    private String side;   // BUY / SELL

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 10)
    private BigDecimal filledQuantity;  // cumulative executed quantity

    @Column(precision = 10, scale = 2)
    private BigDecimal avgPrice;        // weighted average execution price

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "account_id", nullable = false, length=30)
    private String accountId;

    public void applyExecution(BigDecimal execQty, BigDecimal execPrice) {

        if (filledQuantity == null) {
            filledQuantity = BigDecimal.ZERO;
        }
        if (avgPrice == null) {
            avgPrice = BigDecimal.ZERO;
        }
        // cumulative filled quantity
        BigDecimal newFilled = filledQuantity.add(execQty);
        // weighted average price
        BigDecimal oldCost = filledQuantity.multiply(avgPrice);
        BigDecimal newCost = oldCost.add(execQty.multiply(execPrice));
        BigDecimal newAvg = newCost.divide(newFilled, 8, java.math.RoundingMode.HALF_UP);
        this.filledQuantity = newFilled;
        this.avgPrice = newAvg;
        // update status
        if (newFilled.compareTo(filledQuantity) < 0) {
            this.status = OrderStatus.PARTIALLY_FILLED;
        } else {
            this.status = OrderStatus.FILLED;
        }
        this.updatedAt = LocalDateTime.now();
    }
    public void addExecution(OrderExecutions execution) {
        execution.setOrderId(this.id);
        //this.executions.add(execution);
    }
}