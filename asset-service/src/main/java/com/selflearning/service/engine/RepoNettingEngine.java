package com.selflearning.service.engine;

import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.model.PricingResponse;
import com.selflearning.service.PortfolioService;
import com.selflearning.service.impl.PricingClient;
import com.selflearning.service.reportGenerator.ReportProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class RepoNettingEngine extends BaseNettingEngine{

    private final PortfolioService portfolioService;
    private final PricingClient pricingClient;
    public RepoNettingEngine(PortfolioService portfolioService, PricingClient pricingClient) {
        this.portfolioService = portfolioService;
        this.pricingClient = pricingClient;
    }

    @Override
    public List<String> enrichTrades(NettingRequestDto nettingRequestDto) {
        log.info("Processing REPO Netting...");
        return new ArrayList<>();
    }

    @Override
    public List<String> generateBuckets(List<String> trades) {
        log.info("Processing REPO Netting...");
        var portfolios = portfolioService.getPortfolios();
        log.info(portfolios.toString());
        return new ArrayList<>();
    }

    @Override
    public NettingResponseDto processNetting(List<String> buckets) {
        log.info("Processing REPO Netting...");
        PricingResponse price = //new PricingResponse(AssetTypeEnum.REPO.getCode(), 37.8, "AED");
                pricingClient.fetchPrice("12345", AssetTypeEnum.REPO.getCode(), "AED");

        NettingResponseDto response = new NettingResponseDto();
        response.setNetAmount(new BigDecimal(price.getPrice()));
        response.setAssetType(AssetTypeEnum.REPO.getCode());
        return response;
    }

    @Override
    public void generateReport(NettingRequestDto request, BigDecimal netAmount, List<String> buckets) {
        CompletableFuture<String> future = ReportProcessor.nettingReport();
        future.thenAccept(result -> {
            System.out.println("Netting Report finished: " + result);
        });
        //reportExecutor.submit(ReportProcessor.nettingReport());
    }
}