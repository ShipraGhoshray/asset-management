package com.selflearning.service.reportGenerator;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class NettingReportGenerator{
    private String reportId;
    private String assetType;
    private String portfolioId;
    private String BrokerId;
    private String custodianId;
    private BigDecimal netAmount;
    private LocalDate valuationDate;
    private List<String> trades;
}