package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration

/**
 * 201229 | osj4532 | created
 */

@Configuration
@EnableCaching
class RedisConfig (
    @Value("\${spring.redis.host}") val host: String,
    @Value("\${spring.redis.port}") val port: Int,
    val ctx: ApplicationContext
){
    companion object {
        private val log = LoggerFactory.getLogger(RedisConfig::class.java) as Logger
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        log.info("RedisCacheConfig host: $host")
        log.info("RedisCacheConfig port: $port")

        // 추후 환경에 따라 redis 를 이용하고 싶다면 이용할 것
        // val env = ctx.getBean("environment") as Environment
        return LettuceConnectionFactory(
            RedisClusterConfiguration().clusterNode(host, port))
    }

    @Bean
    fun redisConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofDays(1))
            .disableCachingNullValues()
    }

    @Bean
    fun cacheManager(): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(redisConfiguration())
            .transactionAware()
            .build()
    }
}