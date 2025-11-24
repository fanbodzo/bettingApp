package com.fifi.bettingApp.repository;

import com.fifi.bettingApp.entity.CompanyFinances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFinancesRepository extends JpaRepository<CompanyFinances,Integer> {
}
