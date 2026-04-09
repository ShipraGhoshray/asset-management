package com.selflearning.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "pricing")
public class PricingProperties {
    private List<String> assets;
    private Map<String, Double> thresholds;
}