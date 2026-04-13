package com.selflearning.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    private String id;
    private String portfolioId;       // e.g., Bond, Equity, Derivative
    private double marketValue;
    private String currency;
}