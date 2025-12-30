package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.admin.CreateEventRequest;
import com.fifi.bettingApp.dto.admin.CreateMarketRequest;
import com.fifi.bettingApp.dto.admin.CreateOddRequest;
import com.fifi.bettingApp.entity.Event;
import com.fifi.bettingApp.entity.Market;
import com.fifi.bettingApp.entity.Odd;
import com.fifi.bettingApp.entity.Sport;
import com.fifi.bettingApp.entity.enums.EventStatus;
import com.fifi.bettingApp.repository.EventRepository;
import com.fifi.bettingApp.repository.MarketRepository;
import com.fifi.bettingApp.repository.OddRepository;
import com.fifi.bettingApp.repository.SportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferManagementServiceImpl implements OfferManagementService {

    private final EventRepository eventRepository;
    private final SportRepository sportRepository;
    private final MarketRepository marketRepository;
    private final OddRepository oddRepository;

    @Override
    @Transactional
    public Event createEvent(CreateEventRequest request){

        Sport sport = sportRepository.findById(request.sportId())
                .orElseThrow(() -> new EntityNotFoundException("Sport not found with id: " + request.sportId()));

        Event event = Event.builder()
                .sports(sport)
                .eventName(request.eventName())
                .startTime(request.startTime())
                .eventStatus(EventStatus.UPCOMING)
                .build();

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Market createMarket(Long eventId, CreateMarketRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        Market market = Market.builder()
                .event(event)
                .marketName(request.marketName())
                .isSettled(false)
                .build();

        return marketRepository.save(market);
    }

    @Override
    @Transactional
    public Odd createOdd(Long marketId, CreateOddRequest request) {

        Market market = marketRepository.findById(marketId.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Market not found with id: " + marketId));

        Odd odd = Odd.builder()
                .market(market)
                .outcomeName(request.outcomeName())
                .oddValue(request.oddValue())
                .isActive(true)
                .build();

        return oddRepository.save(odd);
    }
}
