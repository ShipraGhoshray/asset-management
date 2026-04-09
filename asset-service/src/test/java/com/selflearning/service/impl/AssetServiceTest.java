package com.selflearning.service.impl;/*
package com.selflearning.service.impl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.service.factory.NettingEngineFactory;
import com.selflearning.service.engine.BaseNettingEngine;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssetServiceTest {

    @Test
    void testGenerateReportWithRepoAsset() {
        // Arrange: mock engine
        BaseNettingEngine mockEngine = Mockito.mock(BaseNettingEngine.class);
        NettingResponseDto dto = new NettingResponseDto();
        dto.setNetAmount(new BigDecimal("2500000"));
        when(mockEngine.processTask(any(NettingRequestDto.class))).thenReturn(dto);

        // Mock factory static call
        try (var mockedFactory = Mockito.mockStatic(NettingEngineFactory.class)) {
            mockedFactory.when(() -> NettingEngineFactory.getNettingInstance("REPO"))
                    .thenReturn(mockEngine);

            NettingRequestDto request = new NettingRequestDto();
            request.setAssetType("REPO");
            request.setPortfolioId("BNY Mellon");
            request.setTrades(List.of("T123", "T124"));

            AssetServiceImpl service = new AssetServiceImpl();
            NettingResponseDto response = service.processTask(request);
            // Assert
            assertEquals("REPO", response.getAssetType());
            assertEquals("BNY Mellon", response.getPortfolioId());
            assertEquals(new BigDecimal("2500000"), response.getNetAmount());
            assertEquals(List.of("T123", "T124"), response.getTrades());
            assertNotNull(response.getReportId());
            assertNotNull(response.getValuationDate());
        }
    }

    @Test
    void testGenerateReportWithUnknownAssetTypeThrowsException() {
        NettingRequestDto request = new NettingRequestDto();
        request.setAssetType("UNKNOWN");
        request.setPortfolioId("TestBank");
        request.setTrades(List.of("T999"));

        AssetServiceImpl service = new AssetServiceImpl();
        assertThrows(RuntimeException.class, () -> service.processTask(request));
    }
}*/
