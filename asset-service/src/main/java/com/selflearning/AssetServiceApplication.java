package com.selflearning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.selflearning.CommonApplication.class, args);
        System.out.print("Asset Service Application started!");
    }
}