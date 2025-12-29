package com.fifi.bettingApp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OddDto {
    private Long oddId;
    private String outcomeName;
    private Double oddValue;
}
