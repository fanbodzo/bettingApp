package com.fifi.bettingApp.entity;

import com.fifi.bettingApp.entity.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    //wiele eventow nalezy do jednego sportu weic
    // wiele obiektow nalezy do jednego obiektu daltego private Sport sport
    //JoinColumn okresla ze to w tabeli events znajduje sie klucz obcy sport_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id" , nullable = false)
    @ToString.Exclude
    private Sport sports;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Market> markets;

}
