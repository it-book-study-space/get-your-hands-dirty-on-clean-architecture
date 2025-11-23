package com.example.demo.account.applicaiton.port.`in`

interface SendMoneyUseCase {
    fun sendMoney(command: SendMoneyCommand): Boolean
}