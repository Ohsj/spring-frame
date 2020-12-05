package kr.co.osj4532.fwk.aop

import ch.qos.logback.classic.Logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * 201205 | osj4532 | created
 */

@Component
@Aspect
class ServiceAdvice {
    companion object {
        private val log = LoggerFactory.getLogger(ServiceAdvice::class.java) as Logger
    }

    /**
     * 서비스 전/후 처리
     */
    @Around("PointCutList.pointService()")
    fun aroundService(joinPoint: ProceedingJoinPoint): Any? {
        val returnVal: Any?
        val elapsed: Long
        val serviceName = "${joinPoint.signature.declaringType.simpleName}.${joinPoint.signature.name}"
        val args = joinPoint.args.toList().joinToString(",")

        val filteredArgs =
            if (args.isNotEmpty()) {
                if (args.length > 100) "with ${args.slice(0..100)}..."
                else "with $args"
            } else {
                ""
            }

        // Main
        try{
            log.info(">>>>> service start [$serviceName()] $filteredArgs")
            elapsed = measureTimeMillis {
                returnVal = joinPoint.proceed()
            }
        }catch(e: Exception) {
            log.error(">>>>> [$serviceName()] occurred error [${e.message}]")
            throw e
        }
        log.info("<<<<< service end [$serviceName()] [$elapsed] ms")
        return returnVal
    }
}