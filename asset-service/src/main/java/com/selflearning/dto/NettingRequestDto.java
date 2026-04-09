package com.selflearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettingRequestDto {

        private String assetType;
        private String portfolioId;
        private String brokerId;
        private String custodianId;
        private List<String> trades;
}