package com.fifi.bettingApp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "markets")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Integer marketId;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "is_settled", nullable = false)
    private boolean isSettled = false;

    //wiele rynkow nalezy do jednego wydarzenia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;

    //jeden rynek moze miec wiele kursow
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Odd> odds;
}
