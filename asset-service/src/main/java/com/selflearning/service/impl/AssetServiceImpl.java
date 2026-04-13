package com.selflearning.service.impl;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
//import com.selflearning.mapper.AssetMapper;
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

@Service
public class AssetServiceImpl implements AssetService {
    private final NettingEngineFactory engineFactory;
    //private final AssetMapper assetMapper;
    private final AssetRepository assetRepository;

    public AssetServiceImpl(NettingEngineFactory engineFactory, //AssetMapper assetMapper,
                            AssetRepository assetRepository) {
        this.engineFactory = engineFactory;
        //this.assetMapper = assetMapper;
        this.assetRepository = assetRepository;
    }

    public AssetResponseDto createAsset(AssetRequestDto request) {
        Asset asset = new Asset() ; //assetMapper.toEntity(request);
        asset.setType(request.getType());
        asset.setName(request.getName());
        asset.setDescription(request.getDescription());
        asset.setThreshold(request.getThreshold());
        asset.setCurrency(request.getCurrency());
        asset.setStatus(request.getStatus());
        Asset saved = assetRepository.save(asset);
        return null; //assetMapper.toResponse(saved);
    }

    public AssetResponseDto getAssetById(Long id) {
        Asset asset = assetRepository.getReferenceById(id);
        return null; // assetMapper.toResponse(asset);
    }

    public List<AssetResponseDto> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return null; /*assets.stream()
                .map(assetMapper::toResponse)
                .collect(Collectors.toList());*/
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
