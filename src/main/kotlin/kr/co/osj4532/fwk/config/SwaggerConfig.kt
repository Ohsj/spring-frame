package kr.co.osj4532.fwk.config

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
        return Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("kr.co.osj4532"))
            .paths(PathSelectors.any())
            .build()
            .pathMapping("/")
            .apiInfo(apiInfo())
    }
}