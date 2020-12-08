package kr.co.osj4532.repo.jpa

import kr.co.osj4532.model.entity.TestMst
import org.springframework.data.jpa.repository.JpaRepository

interface TestMstRepo: JpaRepository<TestMst, Long> {
}