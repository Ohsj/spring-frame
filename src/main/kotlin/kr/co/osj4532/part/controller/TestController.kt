package kr.co.osj4532.part.controller

import kr.co.osj4532.part.dto.GetTestOut
import kr.co.osj4532.repo.mybatis.TestMapper
import kr.co.osj4532.repo.mybatis.TestMapper2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController {
    @Autowired lateinit var mapper: TestMapper
    @Autowired lateinit var mapper2: TestMapper2

    @GetMapping
    fun mapperTest(): List<GetTestOut> {
        return mapper.selectTest()
    }

    @GetMapping("/2")
    fun mapperTest2(): List<GetTestOut> {
        return mapper2.selectTest()
    }
}