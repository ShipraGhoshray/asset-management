package com.selflearning.service.engine;

import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.model.PricingResponse;
import com.selflearning.service.impl.PricingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RepoNettingEngine extends BaseNettingEngine{

    private final PricingClient pricingClient;
    public RepoNettingEngine(PricingClient pricingClient) {
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
}