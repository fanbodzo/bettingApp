package com.fifi.bettingApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_finances")
public class CompanyFinances {

    @Id
    private Integer id;

    @Column(name = "total_balance", nullable = false)
    private Double totalBalance;

    @Column(name = "last_calculated_at", nullable = false)
    private LocalDateTime lastCalculatedAt;
}
