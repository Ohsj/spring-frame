package kr.co.osj4532.util

import kr.co.osj4532.AppMainKt
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [AppMainKt::class])
class EncryptUitlsTest {

//    @Autowired lateinit var encryptUtils: EncryptUtils

    @Test
    fun encryptTest() {
        val res = EncryptUtils.encrypt("1234")
        println(res)
    }
}