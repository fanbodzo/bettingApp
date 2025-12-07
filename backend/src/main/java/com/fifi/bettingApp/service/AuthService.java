package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.RegisterRequest;
import com.fifi.bettingApp.entity.User;
import com.fifi.bettingApp.entity.enums.Role;
import com.fifi.bettingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //transactional robi wszystko w ramach jednej operacji bazodanowej jezlei cos sie niepoweidzie
    //to jest robiony rollback transakcji nie leci nic do bazy
    @Transactional
    //tworze klase do rejstrecji ale jak widac oczekuje obiektu z klasey RegisterUser
    //jest to klasa DTO ktora wysyla mi tylko potrzebne dane a nie wsyzstko o obiekcie
    //a obiekt dopeiro teraz tworze wiec ni e ma po co wsyzksiego wysylac
    public void registerUser(RegisterRequest registerRequest) {
        //tutaj sprawdzamy czy isnieje taka nazwa uzytkonika/email
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new IllegalStateException("Username is already in use");
        }
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalStateException("Email is already in use");
        }

        //wazne przy uzywaniu buildera zeby nie zapomniec zadnego pola bo da 0 albo null
        User user = User.builder().username(registerRequest.getUsername())
                .email(registerRequest.getEmail()).firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                //bardzo wazny step haslo musi isc zahashowane
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .personalIdNumber(registerRequest.getPersonalIdNumber())
                .cashBalance(0.0).bonusBalance(0.0)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(Role.ROLE_USER))
                .build();
        //zappisuje do bazy
        userRepository.save(user);
    }

}
