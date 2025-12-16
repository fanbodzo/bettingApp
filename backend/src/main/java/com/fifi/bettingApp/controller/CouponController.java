package com.fifi.bettingApp.controller;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetSelectionDto;
import com.fifi.bettingApp.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    //narazie tlyk oi wylacznie do testow bo nie mam reszty gotwoej a chce sprawsdzi czy cdziala
    private static final BetSelectionDto MOCK  = new BetSelectionDto(
            101L,"Polska - Azerbeijan"
            ,"Wynik meczu","Polska"
            ,1.50
    );

    @GetMapping("/{userId}")
    public ResponseEntity<BetCouponDto> getCouponByUserId(@PathVariable Long userId) {
        BetCouponDto coupon = couponService.getCoupon(userId);
        return ResponseEntity.ok(coupon);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<BetCouponDto> addSelection(@PathVariable Long userId){
        //narazie dodaje tego m ocka sztuczny obiekt do testu
        BetCouponDto updatedCoupon = couponService.addSelectionToCoupon(userId, MOCK);
        return ResponseEntity.ok(updatedCoupon);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<BetCouponDto> deleteSelection(@PathVariable Long userId){
        couponService.clearCoupon(userId);
        return ResponseEntity.noContent().build();
    }
}
