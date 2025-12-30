package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.admin.CreateEventRequest;
import com.fifi.bettingApp.dto.admin.CreateMarketRequest;
import com.fifi.bettingApp.dto.admin.CreateOddRequest;
import com.fifi.bettingApp.entity.Event;
import com.fifi.bettingApp.entity.Market;
import com.fifi.bettingApp.entity.Odd;

public interface OfferManagementService {
    public Event createEvent(CreateEventRequest request);
    public Market createMarket(Long eventId, CreateMarketRequest request);
    public Odd createOdd(Long marketId, CreateOddRequest request);
}
