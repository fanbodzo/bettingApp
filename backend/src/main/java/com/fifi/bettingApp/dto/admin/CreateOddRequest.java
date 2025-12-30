package com.fifi.bettingApp.dto.admin;


public record CreateOddRequest(String outcomeName,
        Double oddValue
) {}
