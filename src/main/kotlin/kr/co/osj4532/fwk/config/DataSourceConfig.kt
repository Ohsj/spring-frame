package kr.co.osj4532.fwk.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

/**
 * 201129 | osj4532 | created
 */

@Configuration
class DataSourceConfig {

    @Primary
    @Bean(name = ["dataSource"])
    fun dataSource(
            @Value("\${spring.datasource.url}") url: String,
            @Value("\${spring.datasource.username}") username: String,
            @Value("\${spring.datasource.password}") password: String,
            @Value("\${spring.datasource.driver-class-name}") driverClassName: String,
    ) : DataSource {
        val dsb = DataSourceBuilder.create()
        dsb.url(url)
        dsb.username(username)
        dsb.password(password)
        dsb.driverClassName(driverClassName)
        return dsb.build()
    }
}