package com.selflearning.controller;

import com.selflearning.dto.NettingResponseDto;
import com.selflearning.dto.NettingRequestDto;
import com.selflearning.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/assets")
//@Tag(name = "Netting API", description = "Endpoints for repo netting")
public class AssetController {

    private final AssetService assetService;
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/{netting}")
    //@Operation(summary = "Submit Netting Repo Request",
       //     description = "Send repo trade details for netting calculation")
    public ResponseEntity<String> processAsset(@RequestBody NettingRequestDto requestDto) {
        NettingResponseDto response = assetService.processTask(requestDto);
        return ResponseEntity.ok(response.getAssetType() + " Processing completed...");
    }
}