package com.selflearning.repository;
import com.selflearning.model.PortfolioReadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioReadModel, Long> {
}
