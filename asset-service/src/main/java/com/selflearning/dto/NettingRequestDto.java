package com.selflearning.dto;

import lombok.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NettingRequestDto {

        private String assetType;
        private String portfolioId;
        private String brokerId;
        private String custodianId;
        private List<String> trades;
}