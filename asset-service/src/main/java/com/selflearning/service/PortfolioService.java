package com.selflearning.service;

import com.selflearning.model.PortfolioReadModel;

import java.util.List;

public interface PortfolioService {
    public List<PortfolioReadModel> getPortfolios();
    public PortfolioReadModel getPortfolioById(long id);
}
