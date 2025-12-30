package com.fifi.bettingApp.controller;

import com.fifi.bettingApp.dto.admin.CreateEventRequest;
import com.fifi.bettingApp.dto.admin.CreateMarketRequest;
import com.fifi.bettingApp.dto.admin.CreateOddRequest;
import com.fifi.bettingApp.dto.admin.SettleMarketRequest;
import com.fifi.bettingApp.entity.Event;
import com.fifi.bettingApp.entity.Market;
import com.fifi.bettingApp.entity.Odd;
import com.fifi.bettingApp.service.OfferManagementService;
import com.fifi.bettingApp.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SettlementService settlementService;
    private final OfferManagementService offerManagementService;

    @PostMapping("/settle/market/{marketId}")
    public ResponseEntity<String> settleMarket(@PathVariable Long marketId, @RequestBody SettleMarketRequest request) {
        settlementService.settleMarket(marketId, request.winningOddId());

        return ResponseEntity.ok("Market with ID: " + marketId + " settled successfully.");
    }

    //zarzadzanie zdarzeniami
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest request) {

        Event newEvent = offerManagementService.createEvent(request);

        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    //zaradzanie marketami
    @PostMapping("/events/{eventId}/markets")
    public ResponseEntity<Market> createMarket(@PathVariable Long eventId, @RequestBody CreateMarketRequest request) {

        Market newMarket = offerManagementService.createMarket(eventId, request);

        return new ResponseEntity<>(newMarket, HttpStatus.CREATED);
    }

    //zarzadzanie stawkami
    @PostMapping("/markets/{marketId}/odds")
    public ResponseEntity<Odd> createOdd(@PathVariable Long marketId, @RequestBody CreateOddRequest request) {

        Odd newOdd = offerManagementService.createOdd(marketId, request);

        return new ResponseEntity<>(newOdd, HttpStatus.CREATED);
    }
}
