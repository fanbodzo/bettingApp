package com.fifi.bettingApp.config;

import com.fifi.bettingApp.dto.BetCouponDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//narzzedzei do rozmowy z redisem troche transaltor
public class RedisConfig {
    //kazemy wywolac springowi ta metode i zwrocony obiekt zarejstrujw s owim kontekscie bobedzie nam potrzebny
    //odajmey zaradzanie tym obiektem springowi
    //doslownie zeby mozna bylo to wstryzkiwac
    @Bean
    //defnincja ze bean jest Typu RedisTemplate i jego kliczem jest string a wartoscia obiekt BetCouponDto
    //czyli obciety kupon o najwjazniejsze rzczy ktore sa nam potrzebne czyli jakby nowe pudelko na kupon mniejsze
    //prosimy o RedisConnetionFactory ejst to  niskopoziomwy elekment ktory ejst potrzbeny od polaczenia sie do redisa
    //spring nam go sam ogarnia
    public RedisTemplate<String, BetCouponDto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //tworzymy instajce podpinamy fabryke
        RedisTemplate<String, BetCouponDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //tworzenie kazdego klucza bedzie zezoumienym stringiem anie ciagiemznakow
        //tworzac kazdy klucz redis bedzie uizywac StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //Jackson to biblioteka do operacji na JSON bardzo popularna
        //jackson odpowaida teraz za obikety BetCoupon
        //.class przekazuje nam dokladne informacje oklasie nie tworzac nowergo obiektu jakie ma pola metody itd
        Jackson2JsonRedisSerializer<BetCouponDto> serializer = new Jackson2JsonRedisSerializer<>(BetCouponDto.class);

        //pozniej mowimy redioswi ze dla kazdej zapisywanej wartosci uzywaj tego serializatora
        redisTemplate.setValueSerializer(serializer);

        //finalizacja konfiguracji beana i przakzanie obiektu dla springa zeby dzialac dalej
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
