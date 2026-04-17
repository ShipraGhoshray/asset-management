package com.selflearning.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    private String id;
    private String portfolioId;       // e.g., Bond, Equity, Derivative
    private double marketValue;
    private String currency;

    public Bucket(String portfolioId, String currency) {
        this.portfolioId = portfolioId;
        this.currency = currency;
    }

    // Equality based on portfolioId
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Bucket bucket))
            return false;
        return Objects.equals(portfolioId, bucket.portfolioId)
                && Objects.equals(currency, bucket.currency) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, currency);
    }

    @Override
    public String toString() {
        return portfolioId + " - " + currency;
    }
}