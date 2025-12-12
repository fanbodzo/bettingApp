package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetSelectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final RedisTemplate<String, BetCouponDto> redisTemplate;
    private static final String COUPON_KEY_PREFIX = "coupon:";
    private static final Duration COUPON_TTL = Duration.ofHours(24);

    @Override
    public BetCouponDto addSelectionToCoupon(Long userId, BetSelectionDto selection) {
        String key = COUPON_KEY_PREFIX + userId;
        BetCouponDto coupon = getCoupon(userId);

        coupon.getSelections().add(selection);
        updateCouponTotals(coupon);

        redisTemplate.opsForValue().set(key, coupon, COUPON_TTL);
        return coupon;
    }

    @Override
    public BetCouponDto getCoupon(Long userId) {
        String key = COUPON_KEY_PREFIX + userId;
        BetCouponDto coupon = redisTemplate.opsForValue().get(key);
        return coupon != null ? coupon : new BetCouponDto();
    }

    @Override
    public void clearCoupon(Long userId) {
        String key = COUPON_KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }

    private void updateCouponTotals(BetCouponDto coupon) {
        coupon.setNumberOfSelections(coupon.getSelections().size());

        double totalOdd = coupon.getSelections().stream()
                .mapToDouble(BetSelectionDto::getOddValue)
                .reduce(1.0, (a, b) -> a * b);
        coupon.setTotalOdd(totalOdd);
    }
}
