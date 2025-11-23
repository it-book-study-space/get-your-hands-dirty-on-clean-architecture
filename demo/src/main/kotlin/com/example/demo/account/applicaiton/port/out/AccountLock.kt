package com.example.demo.account.applicaiton.port.out

import com.example.demo.account.domain.Account

interface AccountLock {
    fun lockAccount(accountId: Account.AccountId)
    fun releaseAccount(accountId: Account.AccountId)
}