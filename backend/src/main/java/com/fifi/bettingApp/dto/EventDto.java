package com.fifi.bettingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long eventId;
    private String eventName;
    private LocalDateTime startTime;
    private List<MarketDto> markets = new ArrayList<>();
}