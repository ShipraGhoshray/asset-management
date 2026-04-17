package com.selflearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService reportExecutor() {
        // Fixed thread pool with 3 threads for report generation
        return Executors.newFixedThreadPool(3);
    }
}