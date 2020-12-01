package kr.co.osj4532.fwk.base

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.*

/**
 * 201126 | osj4532 | created
 * 201201 | osj4532 | swagger add
 */

@Configuration
@ComponentScan(basePackages = ["kr.co.osj4532"], lazyInit = true)
@EnableTransactionManagement
@EnableEncryptableProperties
class BaseConfig : WebMvcConfigurer{

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)

        registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
            .resourceChain(false)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods("OPTIONS", "GET", "POST", "PATCH", "PUT", "DELETE")
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON)
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        super.addViewControllers(registry)

        registry.addViewController("/swagger-ui/")
            .setViewName("forward:/swagger-ui/index.html")
    }
}