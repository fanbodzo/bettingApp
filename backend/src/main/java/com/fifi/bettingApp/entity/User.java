package com.fifi.bettingApp.entity;

import com.fifi.bettingApp.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


//adnotacje lombok
//gerneruejmy przy uzyciu lomboka (narzedzie ktore pozwala nam unikac boilerplate code( powatarzyacy sie kod))
//settery , gettery, toString() , equals() , hashCode()
@Data

/*udostepnnienie wzorca projektowego builder
    dziala to prosciutko , zamiast towrzyc powalony konstruktor
    ktory jest nie czytaleny i nie eelastyczny
    generujemy przy izcyiu wzorca obviekt ktory ejst elsatyczny nie musimy tam
    generowac kazdej wartosci mozemy pominiac ia utomatycznie da null dla obiektow albo 0 dla liczb
    czyli bedzie to
    User user = new User().builder.(i tutaj wartosci tez pokropkach )
 */
@Builder
//te dwa obvious
@AllArgsConstructor
@NoArgsConstructor

//adnotacje jpa
//jakarta persistence definiuje mapowanie obiektow do bazy
//automatycnzie mapuje
@Entity
//waskzanie tabeli z jakiej bierzemy dane , nazwa musi odpowiadac tej z bazy
@Table(name = "users")
public class User {

    //te adnotacje daja inforamcje o polu
    //id to wiadomo ze klucz glowny
    @Id
    //deleguje nam generownaie klucza wartosci do naszej bazy
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //nazwa kolumny jaka beirzemymusi sie zgadzac z nazwa w bazie
    @Column(name = "user_id")
    private Long userId;

    //jak widac mozemy dawac kolejen atrybuty tutaj
    @Column(name = "username" , nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash" , nullable = false)
    private String passwordHash;

    @Column(name = "email" , nullable = false, unique = true , length = 100)
    private String email;

    @Column(name = "cash_balance" , nullable = false)
    private Double cashBalance;

    @Column(name = "bonus_balance" , nullable = false)
    private Double bonusBalance;

    @Column(name = "created_at", updatable = false , nullable = false)
    private LocalDateTime createdAt;

    /*
    nie uzywam tutaj @ManyToMany z prostej przyczyny bo nasza encja ma proste wartosci
    i nie sa zlozone i nie maja wlasnych cyklow zycia
    ElementColelction informuej jpa ze jest to zbior prostych typow , fetch type .Eager mowi ze wraz z pobraniem
    usera jest pobierana odarzu rola
    CollectionTable konfiguruje tabele laczaca
    name : nazwa tabeli w bazie danych (user_roles)
    joinColumns: definiuje klucz obcy (user_id) w tabeli user_roles ktory wskazuje
    na uzytkownika (z tabeli users) do którego należy dana rola
    @Enumerted kaze zapisac nazwe roli anie jako liczbe
    @Column to wiadomo kolumna wktorej przechowywana jest wartosc
    @Builder.Default zapewnia zapenwia ze zostanie to zainicajlizowane jako pisty HashSet
     */
    @ElementCollection(targetClass = Role.class , fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    //TODO dodac relacje jak skoncze mapowac baze
}
