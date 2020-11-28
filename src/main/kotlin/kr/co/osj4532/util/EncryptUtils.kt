package kr.co.osj4532.util

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * 201126 | osj4532 | created
 */

@Component
class EncryptUtils {
    @Autowired lateinit var ctx: ApplicationContext
    @Value("\${app.passwordKey}") lateinit var passwordKey: String

    lateinit var encryptor: PooledPBEStringEncryptor

    @PostConstruct
    fun initialize() {
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
        instance = this
        this.encryptor = encryptor
    }

    companion object {
        lateinit var instance: EncryptUtils

        /**
         * 암호화
         */
        fun encrypt(input: String): String {
            val serviceEncryptUtils = instance.ctx.getBean(EncryptUtils::class.java)
            return serviceEncryptUtils.encryptor.encrypt(input)
        }

        /**
         * 복호화
         */
        fun decrypt(input: String): String {
            val serviceEncryptUtils = instance.ctx.getBean(EncryptUtils::class.java)
            return serviceEncryptUtils.encryptor.decrypt(input)
        }
    }
}