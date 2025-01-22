package com.example.authuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;

@Configuration
public class RedisConfig {
    @Bean
    public UnifiedJedis unifiedJedis() {
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password("FnA0XbBCsTCnhb4EklQ2diWGab5fVXiS")
                .build();

        return new UnifiedJedis(
                new HostAndPort(
                        "redis-16854.crce175.eu-north-1-1.ec2.redns.redis-cloud.com",
                        16854),
                config
        );
    }
}
