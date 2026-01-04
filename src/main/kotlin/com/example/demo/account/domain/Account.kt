package com.example.demo.account.domain

import java.time.LocalDateTime

open class Account private constructor(
    private val id: AccountId?,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow
) {

    fun getId(): AccountId? = id

    fun calculateBalance(): Money {
        return baselineBalance + activityWindow.calculateBalance(id!!)
    }

    open fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (!mayWithdraw(money))    return false

        val withdrawal = Activity(
            ownerAccountId = id!!,
            sourceAccountId = id,
            targetAccountId = targetAccountId,
            timestamp = LocalDateTime.now(),
            money = money
        )
        activityWindow.addActivity(withdrawal)
        return true
    }

    private fun mayWithdraw(money: Money): Boolean {
        return (calculateBalance() - money).isPositiveOrZero
    }

    open fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit = Activity(
            ownerAccountId = id!!,
            sourceAccountId = sourceAccountId,
            targetAccountId = id,
            timestamp = LocalDateTime.now(),
            money = money
        )
        activityWindow.addActivity(deposit)
        return true
    }

    @JvmInline
    value class AccountId(val value: Long)

    companion object {
        fun withoutId(
            baselineBalance: Money,
            activityWindow: ActivityWindow
        ): Account = Account(null, baselineBalance, activityWindow)

        fun withId(
            accountId: AccountId,
            baselineBalance: Money,
            activityWindow: ActivityWindow
        ): Account = Account(accountId, baselineBalance, activityWindow)
    }
}