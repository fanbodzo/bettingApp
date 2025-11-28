package com.fifi.bettingApp.security;

import com.fifi.bettingApp.entity.User;
import com.fifi.bettingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

//jest to ozanczenie springa ze jest to jego komponent i nalezy do wartwy serwisowej
@Service
//lombok adnotacja ktora generujekonstruktor dla pol final
//czyli zrobi dla nas konstruktor ktory bedzie wstrzykiwac wiec nie musze go sam pisac ale moglbym
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //wstyrzkniecei repozytorium
    private final UserRepository userRepository;

    /**
     * metoda ktora wywoluje spring zeby zaladowac dane uzytkownika
     * @param username nazwa uzytkownika ktory proboje sei zalogowac
     * @return obiekt UserDetails zwarac dane potrzebne do autentykacji
     * @throws UsernameNotFoundException jezeli nie ma uzytkonika w bazie
     */
    @Override
    //transakcja jezeli przejdzie pomyslnie jest robionny commit jezeli siewysypie jest rollback
    //ale jest tutaj tylko do odczytu do pobrania danych fajnie dobra praktyka
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // suzkamy ziomka w bazie
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        //jezeli istnieje mapujemy encje na obiekt
        //ktory czai spring security ktory implementuje interfejs UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                //uzywam strumienia zeby zmapowac zbior rol na kolekcje obiektow GrantedAuthority
                // ktore potrzebuje spring Security
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toSet())
        );
    }
}