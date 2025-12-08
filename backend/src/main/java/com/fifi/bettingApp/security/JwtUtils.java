package com.fifi.bettingApp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/*
    token jwt jest czyms w stylu przepustki dla uzytkonika w aplikacji
    raz wyrobiona moze byc uzywana do rpzechodzenia przz rozne bramki na ktorych sparwdzane sa dane
    i zamiast podawac znowu dane do formulara poakzujemy mam przepustke z wejscia i mam tutaj dostep
 */

@Component
public class JwtUtils {

    //wstrzykniecie tajnego klucza z application.properties
    //to jest jakby matryca pieczeci tora zna tylko nasz serwer
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    //czas waznosci przepustki w milisekundach
    //samo @Value wstryzkuej nam wartosci z plikow
    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    private Key key(){
        //bierzemy nasz sekret jwt i zamieniamy go na bezpieczy hash
        //kryptograficzny klucz uzywany dopodpisywania
        //algorytm HMAC-SHA
        //nie podpisujemy tokneu zwyklym tekstem tylko kluczem ktory rozumieja biblioteki kryptograficzne
        //ta metoda z biblioteki jjtw robi to za nas
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    //generacja tej przepustki
    public String generateJwtToken(Authentication authentication){
        //z obiektu authentication ktory dostajemy po udanym zalogowaniu sie wycigamy dane uzytkownika
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        //builder do budowania tokenu jwt
        //set subject kto jets wlascicileem paszportu unikalny identyfiaktor musi byc
        return Jwts.builder().setSubject((userPrincipal.getUsername()))
                //ustawiamy aktualna godzine wydania i czas przez jaki ejst wazny
                .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                //skladamy w calosc podpisujemy kluczem i mozemy wysylac to jakobezpieczny string
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

}
