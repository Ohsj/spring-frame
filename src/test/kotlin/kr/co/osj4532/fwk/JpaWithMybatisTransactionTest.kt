package kr.co.osj4532.fwk

import org.apache.ibatis.session.SqlSession
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.EntityManagerFactoryUtils
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import kr.co.osj4532.model.entity.*
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManagerFactory

/**
 *
 */

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JpaWithMybatisTransactionTest {
    @Autowired lateinit var emf: EntityManagerFactory
    @Autowired lateinit var sqlSession: SqlSession
    @Autowired lateinit var jtm: JpaTransactionManager

    @Test
    fun jpaWithMybatisBindTogetherOneTransaction() {
        val tt = TransactionTemplate(jtm)
        tt.execute {
            val em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf)
            val test = TestMst(
                    name = "test")
            em?.persist(test)
            val obj: List<Any> = sqlSession.selectList("kr.co.osj4532.repo.mybatis.TestMapper.selectTest", null)
            println("inner selectList size: ${obj.size}")
            em?.flush()
            test
        }
        val obj: List<Any> = sqlSession.selectList("kr.co.osj4532.repo.mybatis.TestMapper.selectTest", null)
        println("outer selectList size: ${obj.size}")
    }
}