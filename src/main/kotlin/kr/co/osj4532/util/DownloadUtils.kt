package kr.co.osj4532.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.net.URLEncoder
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

/**
 * 201210 | osj4532 | created
 */
@Component
class DownloadUtils {
    @Autowired lateinit var request: HttpServletRequest

    @PostConstruct
    fun init() {
        instance = this
    }

    companion object {
        lateinit var instance: DownloadUtils

        fun download(byteArray: ByteArray, fileName: String): ResponseEntity<Resource> {
            val req = instance.request
            val resource = ByteArrayResource(byteArray)
            val userAgent = req.getHeader("User-Agent")
            val downName = if (userAgent.contains("MSIE", true) || userAgent.contains("Trident", true))
                                URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20")
                           else String(fileName.toByteArray(), Charsets.ISO_8859_1)

            val headers = HttpHeaders()
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${downName}")
            headers.add(HttpHeaders.TRANSFER_ENCODING, "binary")

            val contentType = "application/octet-stream; charset=utf-8"
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .headers(headers)
                    .contentLength(byteArray.size.toLong())
                    .body(resource)
        }
    }
}