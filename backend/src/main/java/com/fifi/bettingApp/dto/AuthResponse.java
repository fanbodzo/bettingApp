package com.fifi.bettingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    //ta klasa zaweira token JWT
    //narazie tylko token cos bedzie mozna dodac kiedys
    private String token;
}
