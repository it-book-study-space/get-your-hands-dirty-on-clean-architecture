package com.example.demo.adapter.`in`.web

import com.example.demo.account.applicaiton.port.`in`.SendMoneyCommand
import com.example.demo.account.applicaiton.port.`in`.SendMoneyUseCase
import com.example.demo.account.domain.Account
import com.example.demo.account.domain.Money
import com.example.demo.common.WebAdapter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase
) {

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable("amount") amount: Long
    ) {
        sendMoneyUseCase.sendMoney(
            SendMoneyCommand(
                Account.AccountId(sourceAccountId),
                Account.AccountId(targetAccountId),
                Money.of(amount)
            )
        )
    }
}