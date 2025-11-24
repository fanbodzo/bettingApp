package com.fifi.bettingApp.repository;

import com.fifi.bettingApp.entity.BetSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetSelectionRepository extends JpaRepository<BetSelection, Long> {
}
