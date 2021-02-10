package com.abin.ucenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/29 13:56
 */
//@Configuration
public class RedisConfig {


    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory
                                                           redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }

}
