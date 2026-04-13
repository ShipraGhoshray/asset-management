package com.selflearning.mapper;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.model.Asset;
import org.springframework.stereotype.Component;
//import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
@Component
public interface AssetMapper {

    //@Mapping(target = "status", expression = "java(request.getStatus() != null ? request.getStatus() : \"Active\")")
    Asset toEntity(AssetRequestDto request);
    AssetResponseDto toResponse(Asset asset);
}