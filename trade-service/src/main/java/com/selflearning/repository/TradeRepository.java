package com.selflearning.repository;

import com.selflearning.model.Executions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Executions, String> {}