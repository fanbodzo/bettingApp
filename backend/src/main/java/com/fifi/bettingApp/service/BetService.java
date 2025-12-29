package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetHistoryDto;

import java.util.List;

public interface BetService {
    void placeBet(Long userId , Double stake);
    List<BetHistoryDto> getUserBets(Long userId);
}
