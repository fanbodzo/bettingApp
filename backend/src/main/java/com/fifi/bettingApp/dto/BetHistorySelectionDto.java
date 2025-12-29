package com.fifi.bettingApp.dto;

import com.fifi.bettingApp.entity.enums.BetStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BetHistorySelectionDto {
    private String eventName;
    private String marketName;
    private String outcomeName;
    private Double oddValue;
    private BetStatus status;
}
