package com.fifi.bettingApp.controller;

import com.fifi.bettingApp.dto.BetHistoryDto;
import com.fifi.bettingApp.dto.PlaceBetRequestDto;
import com.fifi.bettingApp.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bets")
@RequiredArgsConstructor
public class BetController {
    private final BetService betService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> placeBet(@PathVariable Long userId , @RequestBody PlaceBetRequestDto requestDto){
        betService.placeBet(userId, requestDto.getStake());

        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<BetHistoryDto>> getUserBets(@PathVariable Long userId) {

        return ResponseEntity.ok(betService.getUserBets(userId));
    }
}
