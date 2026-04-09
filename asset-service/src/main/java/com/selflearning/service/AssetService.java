package com.selflearning.service;

import com.selflearning.dto.NettingResponseDto;
import com.selflearning.dto.NettingRequestDto;

public interface AssetService {
    public NettingResponseDto processTask(NettingRequestDto request);
}
