package kr.co.osj4532.repo.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.osj4532.model.entity.QTestMst
import kr.co.osj4532.model.entity.TestMst
import org.springframework.stereotype.Repository

@Repository
class QTestMstRepo(
    val jpaQueryFactory: JPAQueryFactory
) {
    fun selectAllTest(): List<TestMst>? {
        return jpaQueryFactory.selectFrom(QTestMst.testMst)
            .where(QTestMst.testMst.id.between(12, 14))
            .fetch()
    }
}