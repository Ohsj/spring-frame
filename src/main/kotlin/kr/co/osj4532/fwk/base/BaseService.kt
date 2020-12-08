package kr.co.osj4532.fwk.base

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

@Transactional
class BaseService: BaseObject() {
    protected final val log = LoggerFactory.getLogger(this::class.java) as Logger
}