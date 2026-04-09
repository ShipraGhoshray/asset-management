package com.selflearning.service.impl;

import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.service.AssetService;
import com.selflearning.service.engine.BaseNettingEngine;
import com.selflearning.service.factory.NettingEngineFactory;
import com.selflearning.service.reportGenerator.NettingReportGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AssetServiceImpl implements AssetService {

    private final NettingEngineFactory engineFactory;
    public AssetServiceImpl(NettingEngineFactory engineFactory) {
        this.engineFactory = engineFactory;
    }

    public NettingResponseDto processTask(NettingRequestDto request) {

        BaseNettingEngine engine = engineFactory.getNettingInstance(request.getAssetType());
        NettingResponseDto responseDto = engine.processTask(request);

        // Step 2: Builder assembles the domain report
        NettingReportGenerator report = NettingReportGenerator.builder()
                .reportId(UUID.randomUUID().toString())
                .assetType(request.getAssetType())
                .portfolioId(request.getPortfolioId())
                .netAmount(responseDto.getNetAmount())
                .valuationDate(LocalDate.now())
                .trades(request.getTrades())
                .build();

        // Step 3: Map domain object to DTO
        NettingResponseDto response = new NettingResponseDto();
        response.setReportId(report.getReportId());
        response.setAssetType(report.getAssetType());
        response.setPortfolioId(report.getPortfolioId());
        response.setNetAmount(report.getNetAmount());
        response.setValuationDate(report.getValuationDate());
        response.setTrades(report.getTrades());
        return response;
    }
}
