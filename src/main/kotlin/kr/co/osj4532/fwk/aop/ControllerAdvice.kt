package kr.co.osj4532.fwk.aop

import ch.qos.logback.classic.Logger
import org.apache.commons.io.IOUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.system.measureTimeMillis


/**
 * 201205 | osj4532 | created
 */

@Component
@Aspect     // AOP 기능을 하는 클래스의 레벨을 지정하는 애너테이션
class ControllerAdvice {
    companion object {
        private val log = LoggerFactory.getLogger(ControllerAdvice::class.java) as Logger
    }

    @Autowired lateinit var request: HttpServletRequest

    /**
     * Advice 는 모두 다섯 가지의 타입이 있다.
     * 다섯 가지의 어라운드는 메서드의 호출 자체를 제어할 수 있기 때문에
     * 어드바이스  중 가장 강력한 기능이라고 볼 수 있다.
     *
     * 1. Before Advice | @Before | Target 메서드 호출 이전에 적용할 어드바이스 정의
     * 2. After Advice | @AfterReturning | Target 메서드가 성공적으로 실행되고, 결과값을 반환한 뒤에 적용
     * 3. After throwing | @AfterThrowing | Target 메서드에서 예외 발생 이후에 적용 (try/catch 문에서 catch 와 유사하다.)
     * 4. After | @After | Target 메서드에서 예외 발생에 관계없이 적용 (try/catch 문에서 finally 와 유사하다.)
     * 5. Around | @Around | Target 메서드 호출 이전과 이후 모두 적용 (가장 관범위하게 사용)
     *
     * Around 안에서 execution 으로 시작하는 구문은 포인트컷을 지정하는 문법으로
     * execution 을 제외한 명시자는 within 과 bean 이 있다.
     * 접근 제어자, 리턴 타입, 타입 패턴, 메서드, 파라미터 타입, 예외 타입 등을 조합해서 정교한 포인트컷을 만들 수 있다.
     *
     */

    /**
     * 컨트롤러 전/후 처리
     */
    @Around("PointCutList.pointController()")
    fun aroundController(joinPoint: ProceedingJoinPoint): Any? {
        // Init
        val controllerName = "${joinPoint.signature.declaringType.simpleName}.${joinPoint.signature.name}"
        val elapsed: Long
        val returnVal: Any?

        // Main
        try{
            log.info("session ID : ${request.session.id}")
            log.info("client IP : ${request.getHeader("real-ip")}")
            log.info("GID : ${UUID.randomUUID()}")
            log.info("method : ${request.method}")
            log.info("request URL : ${request.requestURI}")
            log.info("query String :  ${request.queryString}")

            var body = IOUtils.toString(request.inputStream, Charset.forName("UTF-8"))
            body = body.replace("\n", "")
            log.info("body :  $body")
            log.info("referer :  ${request.getHeader("referer")}")
            log.info(">>>>> controller start [$controllerName()]")
            elapsed = measureTimeMillis {
                returnVal = joinPoint.proceed()
            }
        } catch(e: Exception) {
            log.info(">>>>> controller end [$controllerName()] with error [${e.javaClass.simpleName}]")
            throw e
        }

        // End
        log.info("<<<<< controller end [$controllerName()] [$elapsed] ms")
        return returnVal
    }
}