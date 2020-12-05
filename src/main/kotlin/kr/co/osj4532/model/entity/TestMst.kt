package kr.co.osj4532.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "test")
data class TestMst (
        @Id
        // GenerationType.IDENTITY 쓰기 지연 X
        val id: Int = 0,
        val name: String? = null
)