package kr.co.osj4532.part.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.co.osj4532.kr.co.osj4532.part.service.TestService
import kr.co.osj4532.part.dto.GetTestOut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 201129 | osj4532 | created
 */

@RestController
@RequestMapping("/test")
@Api("테스트 컨트롤러")
class TestController {
    @Autowired lateinit var service: TestService

    @GetMapping
    @ApiOperation("메퍼 테스트 API")
    fun mapperTest(): List<GetTestOut> {
        return service.mapperTest()
    }

    @GetMapping("/2")
    @ApiOperation("메퍼2 테스트 API")
    fun mapperTest2(): List<GetTestOut> {
        return service.mapperTest2()
    }
}