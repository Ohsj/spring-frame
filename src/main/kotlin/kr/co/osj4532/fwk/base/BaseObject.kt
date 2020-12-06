package kr.co.osj4532.kr.co.osj4532.fwk.base

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.http.HttpServletRequest

abstract class BaseObject {
    companion object {
        @JvmStatic
        protected val log = LoggerFactory.getLogger("fileError") as Logger
    }

    @Autowired lateinit var request: HttpServletRequest
}