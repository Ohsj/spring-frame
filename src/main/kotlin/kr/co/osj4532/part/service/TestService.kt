package kr.co.osj4532.kr.co.osj4532.part.service


import kr.co.osj4532.part.dto.GetTestOut
import kr.co.osj4532.repo.mybatis.TestMapper
import kr.co.osj4532.repo.mybatis.TestMapper2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 201201 | osj4532 | created
 */

@Service
class TestService {
    @Autowired lateinit var mapper: TestMapper
    @Autowired lateinit var mapper2: TestMapper2

    fun mapperTest(): List<GetTestOut> {
        return mapper.selectTest()
    }

    fun mapperTest2(): List<GetTestOut> {
        return mapper2.selectTest()
    }
}