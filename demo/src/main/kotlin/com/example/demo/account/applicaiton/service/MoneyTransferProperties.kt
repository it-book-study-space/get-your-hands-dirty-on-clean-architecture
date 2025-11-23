package com.example.demo.account.applicaiton.service

import com.example.demo.account.domain.Money
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "money.transfer")
data class MoneyTransferProperties(
    private var _maximumTransferThreshold: Long = 1_000_000L
) {
    val maximumTransferThreshold: Money
        get() = Money.of(_maximumTransferThreshold)

    fun setMaximumTransferThreshold(value: Long) {
        _maximumTransferThreshold = value
    }
}
