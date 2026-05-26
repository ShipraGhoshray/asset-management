package com.selflearning.repository;

import com.selflearning.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findBySymbol(String symbol);
    Optional<Order> findById(String id);
}
