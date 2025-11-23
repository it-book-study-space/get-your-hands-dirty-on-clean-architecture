package com.example.demo.adapter.out.persistence

import com.example.demo.account.applicaiton.port.out.LoadAccountPort
import com.example.demo.account.applicaiton.port.out.UpdateAccountStatePort
import com.example.demo.account.domain.Account
import com.example.demo.common.PersistenceAdapter
import java.time.LocalDateTime

@PersistenceAdapter
class AccountPersistenceAdapter() : LoadAccountPort, UpdateAccountStatePort {
    override fun loadAccount(accountId: Account.AccountId, baselineDate: LocalDateTime): Account {
        TODO("Not yet implemented")
    }

    override fun updateActivities(account: Account) {
        TODO("Not yet implemented")
    }
}