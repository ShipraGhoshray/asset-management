package com.selflearning.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDto {
    private String type;
    private String name;
    private String description;
    private BigDecimal threshold;
    private String currency;
    private String status;
}
