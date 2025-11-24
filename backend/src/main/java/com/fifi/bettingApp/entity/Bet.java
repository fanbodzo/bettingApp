package com.fifi.bettingApp.entity;

import com.fifi.bettingApp.entity.enums.BetStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Long betId;

    // Używamy Double zgodnie z naszą umową.
    @Column(name = "stake", nullable = false)
    private Double stake; // Stawka zakładu

    @Column(name = "total_odd", nullable = false)
    private Double totalOdd; // Łączny kurs

    @Column(name = "potential_payout", nullable = false)
    private Double potentialPayout; // Potencjalna wygrana

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_status", nullable = false)
    private BetStatus betStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //wiele zazkladow moze byc posatwionych przez usera
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    //jeden zaklad moze miec wiele wybranych zdarzen
    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<BetSelection> selections;
}
