package kr.co.osj4532.util

import kr.co.osj4532.AppMainKt
import kr.co.osj4532.kr.co.osj4532.util.SampleUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [AppMainKt::class])
class SampleUtilTest {

//    @Autowired lateinit var sampleUtils: SampleUtils

    @Test
    fun encryptTest() {
        val res = SampleUtils.ttt()
        println(res)
    }
}