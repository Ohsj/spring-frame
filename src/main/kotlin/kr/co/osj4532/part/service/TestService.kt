package kr.co.osj4532.part.service


import kr.co.osj4532.fwk.base.BaseService
import kr.co.osj4532.model.entity.QTestMst
import kr.co.osj4532.model.entity.TestMst
import kr.co.osj4532.part.dto.GetTestOut
import kr.co.osj4532.repo.jpa.TestMstRepo
import kr.co.osj4532.repo.mybatis.TestMapper
import kr.co.osj4532.repo.mybatis.TestMapper2
import kr.co.osj4532.repo.querydsl.QTestMstRepo
import org.springframework.stereotype.Service

/**
 * 201201 | osj4532 | created
 */

@Service
class TestService(
        val mapper: TestMapper,
        val mapper2: TestMapper2,
        val testRepo: TestMstRepo,
        val qTestMstRepo: QTestMstRepo
): BaseService() {

    fun mapperTest(): List<GetTestOut> {
        return mapper.selectTest()
    }

    fun mapperTest2(): List<GetTestOut> {
        return mapper2.selectTest()
    }

    fun jpaTest(): List<TestMst>? {
        return testRepo.findAll()
    }

    fun queryDslTest(): List<TestMst>? {
        return qTestMstRepo.selectAllTest()
    }
}