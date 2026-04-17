package com.selflearning.service.engine;

import com.selflearning.dto.NettingResponseDto;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.service.impl.PricingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public abstract class BaseNettingEngine {

    public NettingResponseDto processTask(NettingRequestDto nettingRequestDto){
        log.info(nettingRequestDto.getAssetType() + " Processing starts...");
        List<String> enrichedTrades = enrichTrades(nettingRequestDto);
        List<String> buckets = generateBuckets(enrichedTrades);
        NettingResponseDto report = processNetting(buckets);
        generateReport(nettingRequestDto, report.getNetAmount(), buckets);
        postProcess(nettingRequestDto, report);
        return report;
    }

    protected abstract List<String> enrichTrades(NettingRequestDto request);
    protected abstract List<String> generateBuckets(List<String> trades);
    protected abstract NettingResponseDto processNetting(List<String> buckets);
    protected abstract void generateReport(NettingRequestDto request, BigDecimal netAmount, List<String> buckets);

    // Hook method (optional override)
    protected void postProcess(NettingRequestDto request, NettingResponseDto response) {
        // Default: do nothing, subclasses may log or audit
    }
}