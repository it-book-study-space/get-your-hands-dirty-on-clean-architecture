package com.example.demo.account.domain

import com.example.demo.common.AccountTestData.defaultAccount
import com.example.demo.common.ActivityTestData.defaultActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AccountTest {

    @Test
    @DisplayName("계좌 금액 계산")
    fun calculatesBalance() {
        // given
        val accountId = Account.AccountId(1L)
        val account = defaultAccount(
            accountId = accountId,
            baselineBalance = Money.of(555L),
            activityWindow = ActivityWindow(
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(999L)
                ),
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(1L)
                )
            )
        )

        // when
        val balance = account.calculateBalance()

        // then
        assertThat(balance).isEqualTo(Money.of(1555L))
    }

    @Test
    @DisplayName("계좌 송금 성공")
    fun withdrawalSucceeds() {
        // given
        val accountId = Account.AccountId(1L)
        val account = defaultAccount(
            accountId = accountId,
            baselineBalance = Money.of(555L),
            activityWindow = ActivityWindow(
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(999L)
                ),
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(1L)
                )
            )
        )

        // when
        val success = account.withdraw(Money.of(555L), Account.AccountId(99L))

        // then
        assertThat(success).isTrue()
        assertThat(account.activityWindow.activities).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L))
    }

    @Test
    @DisplayName("계좌 송금 실패 - 금액 초과")
    fun withdrawalFailure() {
        // given
        val accountId = Account.AccountId(1L)
        val account = defaultAccount(
            accountId = accountId,
            baselineBalance = Money.of(555L),
            activityWindow = ActivityWindow(
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(999L)
                ),
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(1L)
                )
            )
        )

        // when
        val success = account.withdraw(Money.of(1556L), Account.AccountId(99L))

        // then
        assertThat(success).isFalse()
        assertThat(account.activityWindow.activities).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1555L))
    }

    @Test
    @DisplayName("계좌 예금 성공")
    fun depositSuccess() {
        // given
        val accountId = Account.AccountId(1L)
        val account = defaultAccount(
            accountId = accountId,
            baselineBalance = Money.of(555L),
            activityWindow = ActivityWindow(
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(999L)
                ),
                defaultActivity(
                    targetAccountId = accountId,
                    money = Money.of(1L)
                )
            )
        )

        // when
        val success = account.deposit(Money.of(445L), Account.AccountId(99L))

        // then
        assertThat(success).isTrue()
        assertThat(account.activityWindow.activities).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(2000L))
    }
}