package com.selflearning.service.impl;

import com.selflearning.messaging.events.OrderExecutionEvent;
import com.selflearning.model.Holding;
import com.selflearning.model.Portfolio;
import com.selflearning.repository.HoldingRepository;
import com.selflearning.repository.PortfolioRepository;
import com.selflearning.service.PortfolioService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;

    @Override
    @Transactional
    public void handleOrderExecution(OrderExecutionEvent event) {
        Portfolio portfolio = portfolioRepository
                .findByAccountId(event.accountId())
                .orElseGet(() -> createPortfolio(event.accountId()));

        Holding holding = holdingRepository
                .findByPortfolioAndSymbol(portfolio, event.symbol())
                .orElseGet(() -> createHolding(portfolio, event.symbol()));

        holding.setQuantity(holding.getQuantity().add(event.executedQuantity()));
        holding.setAvgPrice(calculateWeightedAverage(
                holding.getQuantity(),
                holding.getAvgPrice(),
                event.executedQuantity(),
                event.executedPrice()));
        holdingRepository.save(holding);
    }

    private Portfolio createPortfolio(String accountId) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId("PF-" + UUID.randomUUID());
        portfolio.setName("");
        portfolio.setAccountId(accountId);
        portfolio.setCreatedAt(LocalDateTime.now());
        return portfolioRepository.save(portfolio);
    }

    private Holding createHolding(Portfolio portfolio, String symbol) {
        Holding h = new Holding();
        h.setId("HLD-" + UUID.randomUUID());
        h.setPortfolio(portfolio);
        h.setSymbol(symbol);
        h.setQuantity(BigDecimal.ZERO);
        h.setAvgPrice(BigDecimal.ZERO);
        return h;
    }

    private BigDecimal calculateWeightedAverage(BigDecimal oldQty,
                                                BigDecimal oldAvg, BigDecimal executedQty, BigDecimal executedPrice) {
        BigDecimal newQty = oldQty.add(executedQty);
        if (newQty.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalCost = oldQty.multiply(oldAvg)
                .add(executedQty.multiply(executedPrice));
        return totalCost.divide(newQty, 8, RoundingMode.HALF_UP);
    }
}