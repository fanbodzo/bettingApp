package com.fifi.bettingApp.repository;

import com.fifi.bettingApp.entity.Market;
import com.fifi.bettingApp.entity.Odd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {
    Optional<Market> findById(Integer marketId);
}
