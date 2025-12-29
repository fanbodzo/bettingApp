package com.fifi.bettingApp.dto;

import com.fifi.bettingApp.entity.enums.BetStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BetHistoryDto {
    private Long betId;
    private Double stake;
    private Double totalOdd;
    private Double potentialPayout;
    private BetStatus status;
    private LocalDateTime placedAt;
    private List<BetHistorySelectionDto> selections;
}
