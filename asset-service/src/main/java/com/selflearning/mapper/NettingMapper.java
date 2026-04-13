package com.selflearning.mapper;

import com.selflearning.dto.NettingResponseDto;
import com.selflearning.service.reportGenerator.NettingReportGenerator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NettingMapper {
    NettingResponseDto toNettingResponseDto(NettingReportGenerator nettingReport);
}