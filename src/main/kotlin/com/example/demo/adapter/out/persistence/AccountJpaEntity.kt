package com.example.demo.adapter.out.persistence

import jakarta.persistence.*

@Table(name = "account")
@Entity
data class AccountJpaEntity(
    @Id
    @GeneratedValue
    val id: Long
)