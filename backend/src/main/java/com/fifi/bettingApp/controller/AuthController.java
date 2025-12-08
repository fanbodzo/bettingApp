package com.fifi.bettingApp.controller;


import com.fifi.bettingApp.dto.AuthResponse;
import com.fifi.bettingApp.dto.LoginRequest;
import com.fifi.bettingApp.dto.MessageResponse;
import com.fifi.bettingApp.dto.RegisterRequest;
import com.fifi.bettingApp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

//mowimy psriongowi ze jest to  kontroler rest a zzwraca json
@RestController
//kazdy endpoint url tutaj bedzie /api/auth
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //pod linkiem -||-/register bedzie rejstracja
    //metoda post sluzy do tworzenia nowego obiektu
    //put do nadpisania caelgo istniejcego obiektu a patch do update jednej wartosci
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try{
            authService.registerUser(registerRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        //probojemy zalogowac
        try{
            //odwoluje sie do serwisu authService do metody loginUser
            AuthResponse authResponse = authService.loginUser(loginRequest);
            //jak sieudalo to zwracam status ok
            return ResponseEntity.ok(authResponse);
        }catch(AuthenticationException e){
            //jak nie udalo sie to jest unauthorized z wiadomoscia
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("error : invalid username or password"));
        }
    }
}
