package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetSelectionDto;

public interface CouponService {
    BetCouponDto addSelectionToCoupon(Long userId, Long oddId);
    BetCouponDto getCoupon(Long userId);
    void clearCoupon(Long userId);
}
