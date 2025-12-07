package com.fifi.bettingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/*
 * dto reprezentujace publicze dane uzytwkonika
 * nie ma tu hasla ani warzliwych danych, bezpeiczne do wyslania jako wiad z api
 * Data Transfer Object to poporstu przepakowanie pol z usera do nowego kontenera
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Double cashBalance;
    private Double bonusBalance;
    private Set<String> roles;
}
