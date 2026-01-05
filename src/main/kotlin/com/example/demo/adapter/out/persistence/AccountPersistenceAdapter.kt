package com.example.demo.adapter.out.persistence

import com.example.demo.account.applicaiton.port.out.LoadAccountPort
import com.example.demo.account.applicaiton.port.out.UpdateAccountStatePort
import com.example.demo.account.domain.Account
import com.example.demo.common.PersistenceAdapter
import jakarta.persistence.EntityNotFoundException
import java.time.LocalDateTime

@PersistenceAdapter
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper
) : LoadAccountPort, UpdateAccountStatePort {

    override fun loadAccount(
        accountId: Account.AccountId,
        baselineDate: LocalDateTime
    ): Account {
        val account = accountRepository.findById(accountId.value)
            .orElseThrow { EntityNotFoundException() }

        val activities = activityRepository.findByOwnerSince(
            accountId.value,
            baselineDate
        )

        val withdrawalBalance = activityRepository
            .getWithdrawalBalanceUntil(accountId.value, baselineDate) ?: 0L

        val depositBalance = activityRepository
            .getDepositBalanceUntil(accountId.value, baselineDate) ?: 0L

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        account.activityWindow.activities
            .filter { it.id == null }
            .forEach { activity ->
                activityRepository.save(accountMapper.mapToJpaEntity(activity))
            }
    }
}