package kr.co.osj4532.repo.mybatis

import kr.co.osj4532.part.dto.GetTestOut
import org.springframework.stereotype.Repository

@Repository
interface TestMapper {
    fun selectTest(): List<GetTestOut>
}