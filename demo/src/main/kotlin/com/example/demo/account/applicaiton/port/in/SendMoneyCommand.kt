package com.example.demo.account.applicaiton.port.`in`

import com.example.demo.account.domain.Account
import com.example.demo.account.domain.Money

data class SendMoneyCommand(
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val money: Money
) {
    init {
        require(money.isPositive) {
            "Transfer money must be positive (received: ${money.amount})"
        }
        require(sourceAccountId != targetAccountId) {
            "Cannot transfer money to the same account (accountId: $sourceAccountId)"
        }
    }
}