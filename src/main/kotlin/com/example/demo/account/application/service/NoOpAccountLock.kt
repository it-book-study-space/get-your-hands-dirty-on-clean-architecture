package com.example.demo.account.application.service

import com.example.demo.account.application.port.out.AccountLock
import com.example.demo.account.domain.Account
import org.springframework.stereotype.Component

@Component
class NoOpAccountLock : AccountLock {
    override fun lockAccount(accountId: Account.AccountId) {
        TODO("Not yet implemented")
    }

    override fun releaseAccount(accountId: Account.AccountId) {
        TODO("Not yet implemented")
    }
}