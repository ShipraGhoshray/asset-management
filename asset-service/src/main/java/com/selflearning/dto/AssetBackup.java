package com.selflearning.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetBackup {
    private String id;
    private String name;
    private String type;       // e.g., Bond, Equity, Derivative
    private String description;
    private double threshold;
    private String currency;
    private String status;
}