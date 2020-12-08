package kr.co.osj4532.fwk.config

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * 201205 | osj4532 | created
 */

@Configuration
@EnableJpaRepositories(basePackages = ["kr.co.osj4532.repo.jpa"],
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
class JpaConfig {
    companion object {
        val log = LoggerFactory.getLogger(JpaConfig::class.java) as Logger
    }

    @Primary
    @Bean(name = ["entityManagerFactory"])
    fun entityManagerFactory(@Qualifier("dataSource") dataSource: DataSource): EntityManagerFactory {
        log.info("EntityManagerFactory Start")
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setDatabase(Database.MYSQL)

        val properties = hashMapOf<String, Any>()
        properties["hibernate.default_schema"] = "query_study"
        properties["hibernate.physical_naming_strategy"] = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"
        // 201208 | osj4532 | show_sql 은 System.out을 사용하기 때문에 성능이 좋지 않다.
        // application.yml 에서 로깅 관련 설정 추가
        //properties["hibernate.show_sql"] = true
        properties["hibernate.format_sql"] = true
        properties["use_sql_comments"] = true
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQL5InnoDBDialect"
        properties["hibernate.ddl-auto"] = "none"
        properties["hibernate.hbm2ddl.auto"] = "none"

        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("kr.co.osj4532.model.entity")
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        entityManagerFactoryBean.setJpaPropertyMap(properties)
        entityManagerFactoryBean.afterPropertiesSet()
        log.info("EntityManagerFactory End")

        return entityManagerFactoryBean.`object`!!
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(@Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory): JpaTransactionManager {
        log.info("TransactionManager Start")
        val res = JpaTransactionManager(entityManagerFactory)
        log.info("TransactionManager End")
        return res
    }
}