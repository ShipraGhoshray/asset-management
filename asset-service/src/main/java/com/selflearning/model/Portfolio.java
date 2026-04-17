package com.selflearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Portfolio implements Comparable<Portfolio>{

    private String id;
    private String portfolioName;

    @Override
    public int compareTo(Portfolio o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return id + " - " + portfolioName;
    }
}
