package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QueryDslConfig (
    val entityManager: EntityManager
){

    companion object {
        private val log = LoggerFactory.getLogger(QueryDslConfig::class.java) as Logger
    }

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        log.info("JPAQueryFactory Start")
        val res = JPAQueryFactory(entityManager)
        log.info("JPAQueryFactory End")
        return res
    }
}