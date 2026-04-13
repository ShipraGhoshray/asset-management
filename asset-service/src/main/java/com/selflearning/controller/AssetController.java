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
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/assets")
//@Tag(name = "Netting API", description = "Endpoints for repo netting")
public class AssetController {

    private final AssetService assetService;
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetResponseDto> createAsset(@RequestBody AssetRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.createAsset(request));

    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id)); // 200 OK
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDto>> getAllAssets() {
        return ResponseEntity.status(201).body(assetService.getAllAssets()); // 201 Created
    }

    @PostMapping("/{netting}")
    //@Operation(summary = "Submit Netting Repo Request",
       //     description = "Send repo trade details for netting calculation")
    public ResponseEntity<String> processAsset(@RequestBody NettingRequestDto requestDto) {
        NettingResponseDto response = assetService.processTask(requestDto);
        return ResponseEntity.ok(response.getAssetType() + " Processing completed...");
    }
}