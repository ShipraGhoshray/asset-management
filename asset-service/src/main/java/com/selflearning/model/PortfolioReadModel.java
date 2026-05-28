package com.selflearning.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "portfolio")
@Immutable
public class PortfolioReadModel {
    @Id
    private String id;
    private String name;
    private String accountId;
    private LocalDateTime createdAt;
}


