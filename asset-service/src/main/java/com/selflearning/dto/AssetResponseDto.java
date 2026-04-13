package com.selflearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDto {
    private String type;
    private String name;
    private String description;
    private BigDecimal threshold;
    private String currency;
    private String status;
}
