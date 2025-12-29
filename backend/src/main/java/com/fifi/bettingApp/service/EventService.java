package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getAllActiveEvents();
}
