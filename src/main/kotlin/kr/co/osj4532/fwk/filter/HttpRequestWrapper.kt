package kr.co.osj4532.fwk.filter

import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * 201206 | osj4532 | copy
 *
 * HttpServletRequest 의 inputStream 은 보안상의 이유 때문에 단 한번만 읽을 수 있도록 만들어졌다.
 * 그래서 HttpServletRequest 의 inputStream 을 여러번 읽으려면 HttpServletRequestWrapper 를 사용해서 필터에 적용 시켜줘야 한다.
 */
class HttpRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private var headerMap = hashMapOf<String, String>()
    private val requestBody: ByteArray

    init {
        val inputStream = super.getInputStream()
        requestBody = IOUtils.toByteArray(inputStream)
    }

    fun addHeader(name: String, value: String) {
        headerMap[name] = value
    }

    override fun getHeader(name: String): String? {
        return headerMap[name]
    }

    override fun getHeaderNames(): Enumeration<String> {
        val names = super.getHeaderNames().toList() as MutableList
        names.addAll(headerMap.keys)
        return Collections.enumeration(names)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        val values = super.getHeaders(name).toList() as MutableList<String>
        if (headerMap.containsKey(name))
            headerMap[name]?.let { values.add(it) }
        return Collections.enumeration(values)
    }

    override fun getRemoteAddr(): String {
        return getHeader("real-ip")?.let {
            getHeader("real-ip")
        }.let {
            super.getRemoteAddr()
        }
    }

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream = ServletImpl(ByteArrayInputStream(requestBody))

    internal inner class ServletImpl(private val inputStream: InputStream) : ServletInputStream() {

        @Throws(IOException::class)
        override fun read(): Int = inputStream.read()

        @Throws(IOException::class)
        override fun read(b: ByteArray): Int = inputStream.read(b)

        override fun isFinished(): Boolean = false
        override fun isReady(): Boolean = false
        override fun setReadListener(listener: ReadListener) {}
    }
}