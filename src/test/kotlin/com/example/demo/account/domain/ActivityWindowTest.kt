package com.example.demo.account.domain

import com.example.demo.common.ActivityTestData.defaultActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ActivityWindowTest {

    @Test
    @DisplayName("계좌 첫 활동 내역")
    fun calculatesStartTimestamp() {
        // given
        val window = ActivityWindow(
            defaultActivity(timestamp = START_DATE),
            defaultActivity(timestamp = IN_BETWEEN_DATE),
            defaultActivity(timestamp = END_DATE)
        )

        // when & then
        assertThat(window.startTimestamp).isEqualTo(START_DATE)
    }

    @Test
    @DisplayName("계좌 마지막 활동 내역")
    fun calculatesEndTimestamp() {
        // given
        val window = ActivityWindow(
            defaultActivity(timestamp = START_DATE),
            defaultActivity(timestamp = IN_BETWEEN_DATE),
            defaultActivity(timestamp = END_DATE)
        )

        // when & then
        assertThat(window.endTimestamp).isEqualTo(END_DATE)
    }

    @Test
    @DisplayName("계좌 금액 계산")
    fun calculatesBalance() {
        // given
        val account1 = Account.AccountId(1L)
        val account2 = Account.AccountId(2L)
        val window = ActivityWindow(
            defaultActivity(
                sourceAccountId = account1,
                targetAccountId = account2,
                money = Money.of(999)
            ),
            defaultActivity(
                sourceAccountId = account1,
                targetAccountId = account2,
                money = Money.of(1)
            ),
            defaultActivity(
                sourceAccountId = account2,
                targetAccountId = account1,
                money = Money.of(500)
            )
        )

        // when & then
        assertThat(window.calculateBalance(account1)).isEqualTo(Money.of(-500))
        assertThat(window.calculateBalance(account2)).isEqualTo(Money.of(500))
    }

    companion object {
        private val START_DATE = LocalDateTime.of(2019, 8, 3, 0, 0)
        private val IN_BETWEEN_DATE = LocalDateTime.of(2019, 8, 4, 0, 0)
        private val END_DATE = LocalDateTime.of(2019, 8, 5, 0, 0)
    }
}