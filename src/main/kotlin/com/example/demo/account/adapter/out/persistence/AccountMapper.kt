package com.example.demo.account.adapter.out.persistence

import com.example.demo.account.domain.Account
import com.example.demo.account.domain.Activity
import com.example.demo.account.domain.ActivityWindow
import com.example.demo.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long,
        depositBalance: Long
    ): Account {
        val baselineBalance = Money.subtract(
            Money.of(depositBalance),
            Money.of(withdrawalBalance)
        )

        return Account.withId(
            Account.AccountId(account.id),
            baselineBalance,
            mapToActivityWindow(activities)
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow {
        val mappedActivities = activities.map { activity ->
            Activity(
                id = activity.id?.let { Activity.ActivityId(it) },
                ownerAccountId = Account.AccountId(activity.ownerAccountId),
                sourceAccountId = Account.AccountId(activity.sourceAccountId),
                targetAccountId = Account.AccountId(activity.targetAccountId),
                timestamp = activity.timestamp,
                money = Money.of(activity.amount)
            )
        }

        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity {
        return ActivityJpaEntity(
            id = activity.id?.value,
            timestamp = activity.timestamp,
            ownerAccountId = activity.ownerAccountId.value,
            sourceAccountId = activity.sourceAccountId.value,
            targetAccountId = activity.targetAccountId.value,
            amount = activity.money.amount.toLong()
        )
    }
}