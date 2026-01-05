package com.example.demo.account.applicaiton.service

import com.example.demo.account.applicaiton.port.`in`.SendMoneyCommand
import com.example.demo.account.applicaiton.port.`in`.SendMoneyUseCase
import com.example.demo.account.applicaiton.port.out.AccountLock
import com.example.demo.account.applicaiton.port.out.LoadAccountPort
import com.example.demo.account.applicaiton.port.out.UpdateAccountStatePort
import com.example.demo.common.UseCase
import jakarta.transaction.Transactional
import java.time.LocalDateTime

@UseCase
@Transactional
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
    private val moneyTransferProperties: MoneyTransferProperties
) : SendMoneyUseCase {

    override fun sendMoney(command: SendMoneyCommand): Boolean {
        checkThreshold(command)

        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount = loadAccountPort.loadAccount(command.sourceAccountId, baselineDate)
        val targetAccount = loadAccountPort.loadAccount(command.targetAccountId, baselineDate)

        val sourceAccountId = requireNotNull(sourceAccount.getId()) {
            "expected source account ID not to be empty"
        }
        val targetAccountId = requireNotNull(targetAccount.getId()) {
            "expected target account ID not to be empty"
        }

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)

        return true
    }

    private fun checkThreshold(command: SendMoneyCommand) {
        if (command.money.isGreaterThan(moneyTransferProperties.maximumTransferThreshold)) {
            throw ThresholdExceededException(
                moneyTransferProperties.maximumTransferThreshold,
                command.money
            )
        }
    }
}