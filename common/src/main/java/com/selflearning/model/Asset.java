package com.selflearning.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    private String id;
    private String name;
    private String type;       // e.g., Bond, Equity, Derivative
    private double marketValue;
    private String currency;
}