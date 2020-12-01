@file:JvmName("AppMain")

package kr.co.osj4532


import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.Environment
import kotlin.system.measureTimeMillis

/**
 * 201125 | osj4532 | created
 */

@SpringBootApplication(
    scanBasePackages = ["kr.co.osj4532"]
)
class AppMainKt {
    companion object {
        private val log = LoggerFactory.getLogger(AppMainKt::class.java) as Logger
        @JvmStatic
        fun main(args: Array<String>) {
            val elapsed = measureTimeMillis {
                log.info("============ AppMain Run START       ============")
                // main start
                val app = runApplication<AppMainKt>(*args) {
                    setBannerMode(Banner.Mode.OFF)
                }

                // profile check
                log.info("============ Check Profile START     ============")
                val env = app.getBean("environment") as Environment
                env.activeProfiles.toList().forEach { log.info(it) }
                log.info("============ Check Profile END       ============ : activate profile count: ${env.activeProfiles.count()}")

                log.info("============ Check Loaded Bean START ============")
                log.info("Loaded Bean : ${app.beanDefinitionNames.toList().sorted().size}")
                log.info("============ Check Loaded Bean END   ============")
            }

            log.info("============ AppMain Run END         ============ : $elapsed ms")
        }
    }
}