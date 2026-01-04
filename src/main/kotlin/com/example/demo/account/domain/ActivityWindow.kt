package com.example.demo.account.domain

import java.time.LocalDateTime

class ActivityWindow(
    activities: List<Activity>
) {

    private val _activities: MutableList<Activity> = activities.toMutableList()

    constructor(vararg activities: Activity) : this(activities.toList())

    val activities: List<Activity>
        get() = _activities.toList()

    val startTimestamp: LocalDateTime
        get() = _activities
            .minByOrNull { it.timestamp }
            ?.timestamp
            ?: throw IllegalStateException()

    val endTimestamp: LocalDateTime
        get() = _activities
            .maxByOrNull { it.timestamp }
            ?.timestamp
            ?: throw IllegalStateException()

    fun addActivity(activity: Activity) {
        _activities.add(activity)
    }

    fun calculateBalance(accountId: Account.AccountId): Money {
        val depositBalance = _activities
            .filter { it.targetAccountId == accountId }
            .map { it.money }
            .fold(Money.ZERO) { acc, money -> Money.add(acc, money) }

        val withdrawalBalance = _activities
            .filter { it.sourceAccountId == accountId }
            .map { it.money }
            .fold(Money.ZERO) { acc, money -> Money.add(acc, money) }

        return Money.add(depositBalance, withdrawalBalance.negate())
    }
}