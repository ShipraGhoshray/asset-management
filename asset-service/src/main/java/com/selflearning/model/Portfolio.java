package com.selflearning.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio implements Serializable, Comparable<Portfolio>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String portfolioCode;

    @Column(name = "name")
    private String portfolioName;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "created_at")
    private String createdAt;

    @Override
    public int compareTo(Portfolio o) {
        return this.portfolioCode.compareTo(o.portfolioCode);
    }

    @Override
    public String toString() {
        return id + " - " + portfolioName;
    }
}