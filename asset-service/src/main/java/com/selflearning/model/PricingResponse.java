package com.selflearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PricingResponse {
    private String assetType;
    private double price;
    private String currency;
}