package com.selflearning.mapper;

import com.selflearning.dto.ExecutionRequest;
import com.selflearning.model.Executions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExecutionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "executionTime", ignore = true)
    @Mapping(target = "reported", ignore = true)
    Executions toEntity(ExecutionRequest requestDto);
}