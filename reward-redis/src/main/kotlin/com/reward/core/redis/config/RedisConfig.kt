package com.reward.core.redis.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class RedisConfig {

    @Value("\${spring.redis.host:redis://localhost}")
    val REDIS_HOST = ""

    @Value("\${spring.redis.port:6379}")
    val REDIS_PORT = ""

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        val useSingleServer = config.useSingleServer()

        useSingleServer.address = "$REDIS_HOST:$REDIS_PORT"

        return Redisson.create(config)
    }
}