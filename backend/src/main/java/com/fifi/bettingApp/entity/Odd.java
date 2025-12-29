package com.fifi.bettingApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odds")
public class Odd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odd_id")
    private Long oddId;

    @Column(name = "outcome_name", nullable = false)
    private String outcomeName;

    @Column(name = "odd_value", nullable = false)
    private Double oddValue;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    //wiele kursow nalezy do jednego rynku
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    @ToString.Exclude
    private Market market;
}
