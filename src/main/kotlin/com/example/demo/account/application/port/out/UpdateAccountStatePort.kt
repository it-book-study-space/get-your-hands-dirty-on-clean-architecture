package com.example.demo.account.application.port.out

import com.example.demo.account.domain.Account

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}