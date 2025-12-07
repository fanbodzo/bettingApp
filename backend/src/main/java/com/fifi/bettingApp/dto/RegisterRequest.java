package com.fifi.bettingApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username cant be empty")
    @Size(min = 3 , max = 20 , message = "Username must be at least 3 letters long to 20 max")
    private String username;

    @NotBlank(message = "Password cant be empty")
    @Size(min = 8 , message = "password must be at least 6 lettes long")
    private String password;

    @NotBlank(message="Email can't be empty")
    @Email(message = "Unvalid format")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Personal ID number is required")
    @Size( min = 10 ,max = 10)
    private String personalIdNumber;
}
