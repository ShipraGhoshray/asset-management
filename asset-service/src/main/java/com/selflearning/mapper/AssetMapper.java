package com.selflearning.mapper;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.model.Asset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetResponseDto toResponseDto(Asset entity);
    Asset toEntity(AssetRequestDto requestDto);
}