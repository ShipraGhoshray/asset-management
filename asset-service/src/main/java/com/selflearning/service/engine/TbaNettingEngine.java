package com.selflearning.service.engine;

import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TbaNettingEngine extends BaseNettingEngine{

    @Override
    protected List<String> enrichTrades(NettingRequestDto request) {
        return List.of();
    }

    @Override
    protected List<String> generateBuckets(List<String> trades) {
        return List.of();
    }

    @Override
    protected NettingResponseDto processNetting(List<String> buckets) {
        return null;
    }

    @Override
    protected void generateReport(NettingRequestDto request, BigDecimal netAmount, List<String> buckets) {

    }
}