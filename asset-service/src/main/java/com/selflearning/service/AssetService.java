package com.selflearning.service;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.dto.NettingRequestDto;

import java.util.List;

public interface AssetService {
    public AssetResponseDto createAsset(AssetRequestDto request);
    public AssetResponseDto getAssetById(Long id);
    public List<AssetResponseDto> getAllAssets();
    public NettingResponseDto processTask(NettingRequestDto request);
}
