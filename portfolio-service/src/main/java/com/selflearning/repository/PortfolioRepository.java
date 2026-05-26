package com.selflearning.repository;

import com.selflearning.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    Optional<Portfolio> findByAccountId(String accountId);
}

