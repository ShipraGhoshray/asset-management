package com.selflearning;

import com.selflearning.model.Portfolio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BootstrapLoaderApp {
    public static void main(String[] args) {
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

        SpringApplication.run(BootstrapLoaderApp.class, args).close(); // terminate after run
    }
}