package com.fifi.bettingApp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BetCouponDto {
    private List<BetSelectionDto> selections = new ArrayList<>();
    private Double totalOdd = 1.0;
    private int numberOfSelections = 0;
}
