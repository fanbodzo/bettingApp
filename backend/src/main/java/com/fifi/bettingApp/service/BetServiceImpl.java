package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.BetCouponDto;
import com.fifi.bettingApp.dto.BetSelectionDto;
import com.fifi.bettingApp.entity.Bet;
import com.fifi.bettingApp.entity.BetSelection;
import com.fifi.bettingApp.entity.User;
import com.fifi.bettingApp.entity.enums.BetStatus;
import com.fifi.bettingApp.repository.BetRepository;
import com.fifi.bettingApp.repository.BetSelectionRepository;
import com.fifi.bettingApp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {
    //wstrzykuje interfejsy bo spring data jpa tworzy w locie za mnie implementacje wiec jest ok
    private final CouponService couponService;
    private final BetSelectionRepository betSelectionRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

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
            BetSelection newSelection = BetSelection.builder()
                    .bet(savedBet)
                    .oddValueAtBetTime(selectionDto.getOddValue())
                    .selectionStatus(BetStatus.PENDING)
                    .build();

            betSelectionRepository.save(newSelection);
        }

        double newBalance = user.getCashBalance() - stake;
        user.setCashBalance(newBalance);
        userRepository.save(user);

        couponService.clearCoupon(userId);

    };
}
