package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetHistoryDto;
import com.fifi.bettingApp.dto.BetHistorySelectionDto;
import com.fifi.bettingApp.dto.BetSelectionDto;
import com.fifi.bettingApp.entity.*;
import com.fifi.bettingApp.entity.enums.BetStatus;
import com.fifi.bettingApp.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {
    //wstrzykuje interfejsy bo spring data jpa tworzy w locie za mnie implementacje wiec jest ok
    private final CouponService couponService;
    private final BetSelectionRepository betSelectionRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final OddRepository oddRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public void placeBet(Long userId , Double stake){
        BetCouponDto coupon = couponService.getCoupon(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("uzytkownik o id " + userId + " nie zostal znaleziony"));

        if(coupon.getSelections().isEmpty()){
            throw new IllegalStateException("Nie mozna postawic zakladu poniewaz kupon jest pusty");
        }
        if(user.getCashBalance() < stake){
            throw new IllegalStateException("nie wystarzcajaca ilsoc srodkow na koncie twoje saldo to: "
            + user.getCashBalance() + " ,  stawka kuponu wynosi: " + stake);
        }

        //postawienie kuponu
        Bet newBet = Bet.builder()
                .stake(stake)
                .totalOdd(coupon.getTotalOdd())
                .potentialPayout(coupon.getTotalOdd()*stake)
                .betStatus(BetStatus.PENDING)
                .user(user)
                .build();
        //zapisanie go w bazie
        Bet savedBet = betRepository.save(newBet);

        //zapisanie selekcji
        for (BetSelectionDto selectionDto : coupon.getSelections()) {

            Odd odd = oddRepository.findById(selectionDto.getOddId())
                    .orElseThrow(() -> new IllegalStateException("Kurs o ID " + selectionDto.getOddId() + " zniknął w trakcie obstawiania!"));

            Market market = marketRepository.findById(selectionDto.getMarketId())
                    .orElseThrow(() -> new IllegalStateException("Rynek o ID " + selectionDto.getMarketId() + " zniknął w trakcie obstawiania!"));

            BetSelection betSelection = BetSelection.builder()
                    .bet(savedBet)
                    .oddValueAtBetTime(selectionDto.getOddValue())
                    .selectionStatus(BetStatus.PENDING)
                    .odd(odd)
                    .market(market)
                    .build();

            betSelectionRepository.save(betSelection);
        }

        double newBalance = user.getCashBalance() - stake;
        user.setCashBalance(newBalance);
        userRepository.save(user);

        couponService.clearCoupon(userId);

    };

    @Override
    @Transactional(readOnly = true)
    public List<BetHistoryDto> getUserBets(Long userId) {
        List<Bet> historyBets = betRepository.findByUserUserIdOrderByCreatedAtDesc(userId);

        return historyBets.stream().map(this::mapToMyBetDto).collect(Collectors.toList());
    }

    private BetHistoryDto mapToMyBetDto(Bet bet) {
        return BetHistoryDto.builder()
                .betId(bet.getBetId())
                .stake(bet.getStake())
                .totalOdd(bet.getTotalOdd())
                .potentialPayout(bet.getPotentialPayout())
                .status(bet.getBetStatus())
                .placedAt(bet.getCreatedAt())
                .selections(bet.getSelections().stream()
                        .map(this::mapToSelectionDto)
                        .collect(Collectors.toList()))
                .build();
    }
    private BetHistorySelectionDto mapToSelectionDto(BetSelection selection) {
        return BetHistorySelectionDto.builder()
                .eventName(selection.getOdd().getMarket().getEvent().getEventName())
                .marketName(selection.getOdd().getMarket().getMarketName())
                .outcomeName(selection.getOdd().getOutcomeName())
                .oddValue(selection.getOddValueAtBetTime())
                .status(selection.getSelectionStatus())
                .build();
    }
}
