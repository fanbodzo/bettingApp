package com.fifi.bettingApp.repository;

import com.fifi.bettingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//bean java springa ktory mowi ze jest to repozytorium (komponent springa)i odpowaida za kontakt z danymi
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
