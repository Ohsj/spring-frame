package kr.co.osj4532.kr.co.osj4532.frame.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * 201126 | osj4532 | created
 *
 * ref : https://www.baeldung.com/spring-boot-jasypt
 */

@Configuration
class JasyptConfig (
    @Value("\${app.passwordKey}") val passwordKey: String
) {
    @Bean("encryptorBean")
    fun stringEncryptor(): StringEncryptor {
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
        return encryptor
    }
}