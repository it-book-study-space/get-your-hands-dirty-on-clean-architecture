package com.example.demo.common

import com.example.demo.account.domain.Account
import com.example.demo.account.domain.Activity
import com.example.demo.account.domain.Money
import java.time.LocalDateTime

object ActivityTestData {

    fun defaultActivity(
        id: Activity.ActivityId? = null,
        ownerAccountId: Account.AccountId = Account.AccountId(42L),
        sourceAccountId: Account.AccountId = Account.AccountId(42L),
        targetAccountId: Account.AccountId = Account.AccountId(41L),
        timestamp: LocalDateTime = LocalDateTime.now(),
        money: Money = Money.of(999L)
    ): Activity {
        return Activity(
            id = id,
            ownerAccountId = ownerAccountId,
            sourceAccountId = sourceAccountId,
            targetAccountId = targetAccountId,
            timestamp = timestamp,
            money = money
        )
    }
}