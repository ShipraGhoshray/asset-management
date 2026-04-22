package com.selflearning.controller;

import com.selflearning.dto.AssetRequestDto;
import com.selflearning.dto.AssetResponseDto;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.dto.NettingResponseDto;
import com.selflearning.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/assets")
@Tag(name = "Assets", description = "Asset service APIs")
public class AssetController {

    private final AssetService assetService;
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get asset by ID")
    public ResponseEntity<AssetResponseDto> getAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id)); // 200 OK
    }

    @GetMapping
    @Operation(summary = "Get All assets", description = "Get All assets")
    public ResponseEntity<List<AssetResponseDto>> getAllAssets() {
        return ResponseEntity.status(201).body(assetService.getAllAssets()); // 201 Created
    }

    @PostMapping("/{netting}")
    @Operation(summary = "Submit Netting Repo Request",
            description = "Send trade details for netting calculation")
    public ResponseEntity<String> processAsset(@RequestBody NettingRequestDto requestDto) {
        NettingResponseDto response = assetService.processTask(requestDto);
        return ResponseEntity.ok(response.getAssetType() + " Processing completed...");
    }
}