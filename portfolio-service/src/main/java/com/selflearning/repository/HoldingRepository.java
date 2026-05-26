package com.selflearning.repository;

import com.selflearning.model.Holding;
import com.selflearning.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, String> {
    Optional<Holding> findByPortfolioAndSymbol(Portfolio portfolio, String symbol);
}

