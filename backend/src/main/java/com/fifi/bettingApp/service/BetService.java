package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;

public interface BetService {
    void placeBet(Long userId , Double stake);
}
