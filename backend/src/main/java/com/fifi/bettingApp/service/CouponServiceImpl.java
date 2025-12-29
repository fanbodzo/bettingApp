package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetSelectionDto;
import com.fifi.bettingApp.entity.BetSelection;
import com.fifi.bettingApp.entity.Odd;
import com.fifi.bettingApp.repository.OddRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final RedisTemplate<String, BetCouponDto> redisTemplate;
    //do unikana jezlei bysmy w redisie chcieli przechowywac takze sesje uzytkonikow
    private static final String COUPON_KEY_PREFIX = "coupon:";
    //TTL - time to live kuponu czyli 24h jakby bedzie aktywny w cache
    private static final Duration COUPON_TTL = Duration.ofHours(24);
    private final OddRepository oddRepository;

    @Override
    public BetCouponDto addSelectionToCoupon(Long userId, Long oddId) {
        Odd odd = oddRepository.findById(oddId)
                .orElseThrow(() -> new IllegalStateException("Kurs o ID " + oddId + " nie zostal znaleziony"));

        BetSelectionDto selectionDto = new  BetSelectionDto(
                odd.getOddId(),
                //spacerek po relacjakch idziemy do encji market i pozniej do event i bierzemy nazwe a jestesmy w odd 
                odd.getMarket().getEvent().getEventName(),
                odd.getMarket().getMarketName(),
                odd.getOutcomeName(),
                odd.getOddValue(),
                odd.getMarket().getMarketId()
        );

        String key = COUPON_KEY_PREFIX + userId;
        BetCouponDto coupon = getCoupon(userId);

        coupon.getSelections().add(selectionDto);
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
