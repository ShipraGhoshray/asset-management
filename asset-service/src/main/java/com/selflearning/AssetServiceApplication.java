package com.selflearning;
import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.model.Bucket;
import com.selflearning.model.Portfolio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.selflearning.repository")
@EntityScan(basePackages = "com.selflearning.model")
@EnableAsync
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

        //Comparable
        List<Portfolio> portfolioList = Arrays.asList(
                new Portfolio("PORT03", "Microsoft Inc"),
                new Portfolio("PORT01", "Apple Inc"),
                new Portfolio("PORT02", "Tesla"),
                new Portfolio("P101", "Equity Fund"),
                new Portfolio("P102", "Growth Fund"),
                new Portfolio("P103", "Bond Fund")
        );
        System.out.println("Before Sorting: " + portfolioList);
        Collections.sort(portfolioList);
        System.out.println("Comparable - After Sorting - by natural order: " + portfolioList);

        //Comparator Sort by portfolioName using Comparator + method reference
        portfolioList.sort(Comparator.comparing(Portfolio::getPortfolioName));
        System.out.println("Comparator - Sorted by Name: " + portfolioList);
        // 3. Sort by portfolioName in reverse order
        portfolioList.sort(Comparator.comparing(Portfolio::getPortfolioName).reversed());
        System.out.println("Comparator - Sorted by Name (Reverse): " + portfolioList);
        // 4. Sort by length of portfolioName using a lambda
        portfolioList.sort((p1, p2)
                -> Integer.compare(p1.getPortfolioName().length(), p2.getPortfolioName().length()));
        System.out.println("Comparator - Sorted by length: " + portfolioList);

        //Streams
        List<String> names = portfolioList.stream()
                .filter(p -> p.getPortfolioName().contains("Fund"))
                .map(Portfolio::getPortfolioName)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(names);

    }
}