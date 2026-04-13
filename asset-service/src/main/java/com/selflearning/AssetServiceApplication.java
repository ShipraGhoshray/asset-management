package com.selflearning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.selflearning.repository")
@EntityScan(basePackages = "com.selflearning.model")
public class AssetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.selflearning.CommonApplication.class, args);
        log.info("Asset Service Application started!");
    }
}