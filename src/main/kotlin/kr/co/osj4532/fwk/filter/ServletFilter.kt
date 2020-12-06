package kr.co.osj4532.kr.co.osj4532.fwk.filter

import ch.qos.logback.classic.Logger
import kr.co.osj4532.fwk.filter.HttpRequestWrapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

/**
 * 201206 | osj4532 | created
 *
 * spring filter 는 Dispatcher servlet 영역에 들어가기 전 front Controller 앞 범위에서 수행된다.
 * 또한 Controller 이후 자원 처리가 끝난 후 응답 처리에 대해서도 변경, 조작을 수행할 수 있다.
 * 보통은 인코딩, XSS 방어를 개발시 사용한다.
 *
 * ref https://velog.io/@sa833591/Spring-Filter-Interceptor-AOP-%EC%B0%A8%EC%9D%B4-yvmv4k96
 */

@Configuration
class ServletFilter: Filter {
    companion object {
        private val log = LoggerFactory.getLogger(ServletFilter::class.java) as Logger
    }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        log.info("ServletFilter Init called")
        super.init(filterConfig)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val requestWrapper = HttpRequestWrapper(request as HttpServletRequest)

        if (requestWrapper.getHeader("X-Forwarded-For") != null) {
            requestWrapper.addHeader("real-ip", requestWrapper.getHeader("X-Forwarded-For")!!)
        } else{
            requestWrapper.addHeader("real-ip", request.remoteAddr)
        }

        // request -> requestWrapper 를 해줌으로써 request 의 inputStream 을 여러번 사용할 수 있다.(body 를 읽어 로깅 가능)
        chain.doFilter(requestWrapper, response)
    }
}