package com.example.demo.common

import com.example.demo.account.domain.Account
import com.example.demo.account.domain.ActivityWindow
import com.example.demo.account.domain.Money

object AccountTestData {

    fun defaultAccount(
        accountId: Account.AccountId = Account.AccountId(42L),
        baselineBalance: Money = Money.of(999L),
        activityWindow: ActivityWindow = ActivityWindow(
            ActivityTestData.defaultActivity(),
            ActivityTestData.defaultActivity()
        )
    ): Account {
        return Account.withId(accountId, baselineBalance, activityWindow)
    }
}