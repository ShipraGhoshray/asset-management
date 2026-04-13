package com.selflearning.service.reportGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Getter
@Setter
public class NettingReportGenerator{
    String reportId;
    String assetType;
    String portfolioId;
    String BrokerId;
    String custodianId;
    BigDecimal netAmount;
    LocalDate valuationDate;
    List<String> trades;
}