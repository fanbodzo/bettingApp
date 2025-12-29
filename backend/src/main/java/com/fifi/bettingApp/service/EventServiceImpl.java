package com.fifi.bettingApp.service;

import com.fifi.bettingApp.dto.EventDto;
import com.fifi.bettingApp.dto.MarketDto;
import com.fifi.bettingApp.dto.OddDto;
import com.fifi.bettingApp.entity.Event;
import com.fifi.bettingApp.entity.Market;
import com.fifi.bettingApp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> getAllActiveEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    private EventDto mapToEventDto(Event event) {
        EventDto dto = new EventDto();
        dto.setEventId(event.getId());
        dto.setEventName(event.getEventName());
        dto.setStartTime(event.getStartTime());

        //mappin grynkow
        List<MarketDto> marketDtos = event.getMarkets().stream()
                .map(this::mapToMarketDto)
                .collect(Collectors.toList());

        dto.setMarkets(marketDtos);
        return dto;
    }

    private MarketDto mapToMarketDto(Market market) {
        MarketDto dto = new MarketDto();
        dto.setMarketId(Long.valueOf(market.getMarketId()));
        dto.setMarketName(market.getMarketName());

        //mapping kursow
        List<OddDto> oddDtos = market.getOdds().stream()
                .map(odd -> new OddDto(odd.getOddId(), odd.getOutcomeName(), odd.getOddValue()))
                .collect(Collectors.toList());

        dto.setOdds(oddDtos);
        return dto;
    }
}