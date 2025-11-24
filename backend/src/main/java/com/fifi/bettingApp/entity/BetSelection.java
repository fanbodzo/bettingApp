package com.fifi.bettingApp.entity;

import com.fifi.bettingApp.entity.enums.BetStatus;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bet_selections")
public class BetSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selection_id")
    private Long selectionId;

    @Column(name = "odd_value_at_bet_time", nullable = false)
    private Double oddValueAtBetTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_status", nullable = false)
    private BetStatus selectionStatus;

    //wiele tymaczsowych kuponow nalezy do bet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id", nullable = false)
    @ToString.Exclude
    private Bet bet;

    //jeden wybor ma jeden kurs ale many to one bo duzo osob moze stawic cn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odd_id", nullable = false)
    @ToString.Exclude
    private Odd odd;

    //wiele tymczasowych kuponow na jednym markecie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    @ToString.Exclude
    private Market market;
}
