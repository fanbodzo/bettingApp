package com.fifi.bettingApp.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MarketDto {
    private Long marketId;
    private String marketName;
    private List<OddDto> odds = new ArrayList<>();
}