package com.example.demo.account.adapter.out.persistence

import com.example.demo.account.adapter.out.persistence.AccountJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountJpaEntity, Long> {
}