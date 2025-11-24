package com.fifi.bettingApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sports")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_id")
    private Long id;

    @Column(name = "sport_name")
    private String sportName;

    //tworze relacje miedzy tabelami
    //jest one to many wiele eventow moze miec jeden sport wiec one to many
    // czyli jeden sport jeden obiekt do listy eventow
    //mapped by wskazuje kto jest wlascicielem relacji tez
    @OneToMany(mappedBy = "sports" , cascade = CascadeType.ALL ,fetch =  FetchType.LAZY)
    @ToString.Exclude
    private Set<Event> events;

}
