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
public class RedisConfig {
    //TODO wyjasnic co to robi
    @Bean
    public RedisTemplate<String, BetCouponDto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, BetCouponDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<BetCouponDto> serializer = new Jackson2JsonRedisSerializer<>(BetCouponDto.class);
        redisTemplate.setValueSerializer(serializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
