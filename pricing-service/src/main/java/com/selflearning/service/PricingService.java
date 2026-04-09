package com.selflearning.service;

import java.math.BigDecimal;

public interface PricingService {
    public BigDecimal calculateMarketValue (String assetType, String currency);

}
