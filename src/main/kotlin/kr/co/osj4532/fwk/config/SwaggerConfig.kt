package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * 201201 | osj4532 | created
 */

@Configuration
class SwaggerConfig {
    companion object {
        private val log = LoggerFactory.getLogger(SwaggerConfig::class.java) as Logger
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Osj4532 Frame")
            .version("1.0.0")
            .description("Frame is created By Osj4532")
            .build()
    }

    @Lazy
    @Bean
    fun api(): Docket {
        log.info("Swagger Config Start")
        val res = Docket(DocumentationType.OAS_30)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("kr.co.osj4532"))
                    .paths(PathSelectors.any())
                    .build()
                    .pathMapping("/")
                    .apiInfo(apiInfo())
        log.info("Swagger Config End")
        return res
    }
}