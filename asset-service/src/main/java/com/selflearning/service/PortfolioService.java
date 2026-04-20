package com.selflearning.service;

import com.selflearning.model.Portfolio;

import java.util.List;

public interface PortfolioService {
    public List<Portfolio> getPortfolios();
    public Portfolio getPortfolioById(long id);
}
