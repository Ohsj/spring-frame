package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.IOException
import javax.sql.DataSource
import kotlin.jvm.Throws

/**
 * 201129 | osj4532 | created
 */

@Configuration
@MapperScan(basePackages = ["kr.co.osj4532.repo.mybatis"],
        sqlSessionFactoryRef = "sqlSessionFactory",
        sqlSessionTemplateRef = "sqlSessionTemplate"
)
class MybatisConfig {
    companion object {
        private val log = LoggerFactory.getLogger(MybatisConfig::class.java) as Logger
    }

    @Bean(name = ["sqlSessionFactory"])
    @Throws(IOException::class)
    fun sqlSessionFactory(@Qualifier("dataSource") ds: DataSource) : SqlSessionFactory {
        log.info("MybatisConfig Start")
        val pathResolver = PathMatchingResourcePatternResolver()
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(ds)
        sqlSessionFactoryBean.setConfigLocation(pathResolver.getResource("classpath:mybatis/mybatis-config.xml"))
        sqlSessionFactoryBean.setMapperLocations(*pathResolver.getResources("classpath:mybatis/mappers/*.xml"))
        sqlSessionFactoryBean.vfs = SpringBootVFS::class.java
        log.info("MybatisConfig End")
        return sqlSessionFactoryBean.`object`!!
    }

    @Bean(name = ["sqlSessionTemplate"])
    fun sqlSessionTemplate(@Qualifier("sqlSessionFactory") sqlSessionFactory: SqlSessionFactory) : SqlSessionTemplate {
        log.info("SqlSessionTemplate Start")
        val res = SqlSessionTemplate(sqlSessionFactory)
        log.info("SqlSessionTemplate End")
        return res
    }
}