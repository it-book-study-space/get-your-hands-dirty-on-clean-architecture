package com.example.demo.account.adapter.out.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "activity")
@Entity
data class ActivityJpaEntity(
    @Id
    @GeneratedValue
    val id: Long?,

    @Column
    val timestamp: LocalDateTime,

    @Column
    val ownerAccountId: Long,

    @Column
    val sourceAccountId: Long,

    @Column
    val targetAccountId: Long,

    @Column
    val amount: Long
)