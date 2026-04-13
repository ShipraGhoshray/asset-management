package com.selflearning.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "asset")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private BigDecimal threshold;

    @Column(length = 3) // ISO currency codes like USD, INR
    private String currency;

    @Column(nullable = false)
    private String status;
}
