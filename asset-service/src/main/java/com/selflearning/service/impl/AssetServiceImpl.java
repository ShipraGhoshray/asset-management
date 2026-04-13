package com.selflearning.service.impl;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.mapper.AssetMapper;
import com.selflearning.mapper.NettingMapper;
import com.selflearning.model.Asset;
import com.selflearning.repository.AssetRepository;
import com.selflearning.service.AssetService;
import com.selflearning.service.engine.BaseNettingEngine;
import com.selflearning.service.factory.NettingEngineFactory;
import com.selflearning.service.reportGenerator.NettingReportGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AssetServiceImpl implements AssetService {
    private final NettingEngineFactory engineFactory;
    private final AssetMapper assetMapper;
    private final NettingMapper nettingMapper;
    private final AssetRepository assetRepository;

    public AssetServiceImpl(NettingEngineFactory engineFactory,
                            AssetMapper assetMapper, NettingMapper nettingMapper,
                            AssetRepository assetRepository) {
        this.engineFactory = engineFactory;
        this.assetMapper = assetMapper;
        this.nettingMapper = nettingMapper;
        this.assetRepository = assetRepository;
    }

    public AssetResponseDto createAsset(AssetRequestDto request) {
        Asset asset = assetMapper.toEntity(request);
        return assetMapper.toResponseDto(assetRepository.save(asset));
    }

    public AssetResponseDto getAssetById(Long id) {
        Asset asset = assetRepository.getReferenceById(id);
        return assetMapper.toResponseDto(asset);
    }

    public List<AssetResponseDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream().map(assetMapper::toResponseDto)
                .collect(Collectors.toList());
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

        /* Step 3: Map domain object to DTO
        NettingResponseDto response = new NettingResponseDto();
        response.setReportId(report.getReportId());
        response.setAssetType(report.getAssetType());
        response.setPortfolioId(report.getPortfolioId());
        response.setNetAmount(report.getNetAmount());
        response.setValuationDate(report.getValuationDate());
        response.setTrades(report.getTrades());*/
        return nettingMapper.toNettingResponseDto(report);
    }
}