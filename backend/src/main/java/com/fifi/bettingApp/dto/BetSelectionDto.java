package com.fifi.bettingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetSelectionDto {
    private Long oddId;
    private String eventName;
    private String marketName;
    private String outcomeName;
    private Double oddValue;
}
