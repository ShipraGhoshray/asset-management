package com.selflearning.service.impl;

import com.selflearning.model.PricingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PricingClient {

    private final WebClient webClient;
    // Inject WebClient.Builder from config
    public PricingClient(WebClient.Builder builder, @Value("${pricing.service.url}") String pricingServiceUrl) {
        this.webClient = builder.baseUrl(pricingServiceUrl).build();
        //this.webClient = builder.baseUrl("http://localhost:8083").build();
    }

    // Fetch price for a given assetId
    public PricingResponse fetchPrice(String assetId, String assetType, String currency) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pricing/value/{id}")
                        .queryParam("assetType", assetType)
                        .queryParam("currency", currency)
                        .build(assetId))
                .retrieve()
                .bodyToMono(PricingResponse.class)
                .block(); // synchronous call
    }
}