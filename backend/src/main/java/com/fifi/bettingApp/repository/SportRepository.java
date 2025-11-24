package com.fifi.bettingApp.repository;

import com.fifi.bettingApp.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository  extends JpaRepository<Sport,Integer> {
}
