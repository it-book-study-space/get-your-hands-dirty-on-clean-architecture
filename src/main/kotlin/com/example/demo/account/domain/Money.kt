package com.example.demo.account.domain

import java.math.BigInteger

data class Money(
    val amount: BigInteger
) {

    val isPositiveOrZero: Boolean
        get() = amount >= BigInteger.ZERO

    val isNegative: Boolean
        get() = amount < BigInteger.ZERO

    val isPositive: Boolean
        get() = amount > BigInteger.ZERO

    fun isGreaterThanOrEqualTo(money: Money): Boolean = amount >= money.amount

    fun isGreaterThan(money: Money): Boolean = amount > money.amount

    operator fun plus(money: Money) = Money(amount + money.amount)

    operator fun minus(money: Money) = Money(amount - money.amount)

    fun negate(): Money = Money(amount.negate())

    companion object {
        val ZERO: Money = of(0L)

        fun of(value: Long): Money = Money(BigInteger.valueOf(value))

        fun add(a: Money, b: Money): Money = Money(a.amount + b.amount)

        fun subtract(a: Money, b: Money): Money = Money(a.amount - b.amount)
    }
}
