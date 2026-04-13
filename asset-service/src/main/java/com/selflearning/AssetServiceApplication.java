package com.selflearning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.selflearning.repository")
@EntityScan(basePackages = "com.selflearning.model")
public class AssetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.selflearning.CommonApplication.class, args);
        System.out.print("Asset Service Application started!");
    }
}