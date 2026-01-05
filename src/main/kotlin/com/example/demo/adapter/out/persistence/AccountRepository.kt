package com.example.demo.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountJpaEntity, Long> {
}