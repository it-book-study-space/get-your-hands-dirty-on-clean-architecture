package com.example.demo.account.domain

import java.time.LocalDateTime

data class Activity(
    val id: ActivityId? = null,
    val ownerAccountId: Account.AccountId,
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val timestamp: LocalDateTime,
    val money: Money
) {

    /**
     * 해당 클래스를 JVM 바이트코드로 컴파일할 때
     * 실제 객체를 생성하지 않고, 내부 값으로 인라인
     */
    @JvmInline
    value class ActivityId(val value: Long)
}
