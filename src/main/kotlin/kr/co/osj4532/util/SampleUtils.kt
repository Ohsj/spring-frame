package kr.co.osj4532.kr.co.osj4532.util

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SampleUtils {
    @Autowired
    lateinit var ctx: ApplicationContext

    @PostConstruct
    fun init() {
        instance = this
    }

    companion object {
        lateinit var instance: SampleUtils
        private val log = LoggerFactory.getLogger(SampleUtils::class.java) as Logger

        fun ttt() : Boolean {
            val tt = instance.ctx.getBean(SampleUtils::class.java) as SampleUtils
            log.info(tt.toString())
            return true
        }
    }
}