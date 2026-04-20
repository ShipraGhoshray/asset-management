package com.selflearning;
import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.model.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.selflearning.repository")
@EntityScan(basePackages = "com.selflearning.model")
@EnableAsync
@EnableCaching
public class AssetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.selflearning.CommonApplication.class, args);
        log.info("Asset Service Application started!");

        //Enumset
        EnumSet<AssetTypeEnum> assetTypeSet = EnumSet.of(AssetTypeEnum.REPO, AssetTypeEnum.REVREPO);
        System.out.println("EnumTypeSet - " + assetTypeSet);
        assetTypeSet.add(AssetTypeEnum.TBA);
        System.out.println("EnumTypeSet - " +assetTypeSet);

        //equals
        Bucket b1 = new Bucket("PORT01", "USD");
        Bucket b2 = new Bucket("PORT02", "YEN");
        Bucket b3 = new Bucket("PORT03", "DHS");
        Bucket b4 = new Bucket("PORT02", "YEN");
        System.out.println("b1 equals b2? " + b1.equals(b2)); // false
        System.out.println("b2 equals b4? " + b2.equals(b4)); // true

    }
}