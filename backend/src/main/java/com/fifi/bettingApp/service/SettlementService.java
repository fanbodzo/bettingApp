package com.fifi.bettingApp.service;

public interface SettlementService {
    public void settleMarket(Long marketId, Long winningOddId);
}
