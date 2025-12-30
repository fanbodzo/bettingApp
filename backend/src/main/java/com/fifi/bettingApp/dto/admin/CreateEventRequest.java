package com.fifi.bettingApp.dto.admin;

import java.time.LocalDateTime;

public record CreateEventRequest(Integer sportId,
        String eventName,
        LocalDateTime startTime
) {}