package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 201126 | osj4532 | created
 *
 * ref : https://www.baeldung.com/spring-boot-jasypt
 */

@Configuration
class JasyptConfig (
    @Value("\${app.passwordKey}") val passwordKey: String
) {

    companion object {
        val log = LoggerFactory.getLogger(JasyptConfig::class.java) as Logger
    }

    @Bean("encryptorBean")
    fun stringEncryptor(): StringEncryptor {
        log.info("Jasypt Config Start")
        val config = SimpleStringPBEConfig()
        config.password = passwordKey
        config.algorithm = "PBEWithMD5AndDES"
        config.setKeyObtentionIterations("1000")
        config.setPoolSize("1")
        config.providerName = "SunJCE"
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.stringOutputType = "base64"

        val encryptor = PooledPBEStringEncryptor()
        encryptor.setConfig(config)
        log.info("Jasypt Config End")
        return encryptor
    }
}