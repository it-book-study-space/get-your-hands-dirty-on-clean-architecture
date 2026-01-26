package com.example.demo.account.application.port.out

import com.example.demo.account.domain.Account
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: Account.AccountId, baselineDate: LocalDateTime): Account
}