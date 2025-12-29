package com.fifi.bettingApp.entity;

import com.fifi.bettingApp.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //wiele transakcji jeden uzytkownik
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    //relacja bo transakcja moze byc powiazana z stawianiem zakladu
    //klucz obcy bo moze byc null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_bet_id")
    @ToString.Exclude
    private Bet relatedBet;
}
