package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.UserDto;
import com.fifi.bettingApp.entity.User;
import com.fifi.bettingApp.entity.enums.Role;
import com.fifi.bettingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //do pobierania userow z bazy
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                //mapowanie kazdego usera na USerDto
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // metoda pomocnicza wywolywania w struimienui , w przyszlosci w osobnym pliku narazei jako testu tuaj jest
    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cashBalance(user.getCashBalance())
                .bonusBalance(user.getBonusBalance())
                .roles(user.getRoles().stream()
                        //konwersja enuma na String
                        .map(Role::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}
