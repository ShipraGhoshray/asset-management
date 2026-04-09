package com.selflearning.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class NettingResponseDto {

    private String reportId;
    private String assetType;
    private String portfolioId;
    private String brokerId;
    private String custodianId;
    private BigDecimal netAmount;
    private LocalDate valuationDate;
    private List<String> trades;
}
