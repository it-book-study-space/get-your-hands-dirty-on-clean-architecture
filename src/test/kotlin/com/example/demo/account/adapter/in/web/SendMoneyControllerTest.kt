package com.example.demo.account.adapter.`in`.web

import com.example.demo.account.applicaiton.port.`in`.SendMoneyCommand
import com.example.demo.account.applicaiton.port.`in`.SendMoneyUseCase
import com.example.demo.account.domain.Account
import com.example.demo.account.domain.Money
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [SendMoneyController::class])
class SendMoneyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var sendMoneyUseCase: SendMoneyUseCase

    @Test
    @DisplayName("송금 테스트")
    fun testSendMoney() {
        mockMvc.perform(
            post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}", 41L, 42L, 500)
                .header("Content-Type", "application/json")
        )
            .andExpect(status().isOk)

        verify(sendMoneyUseCase).sendMoney(
            SendMoneyCommand(
                sourceAccountId = Account.AccountId(41L),
                targetAccountId = Account.AccountId(42L),
                money = Money.of(500L)
            )
        )
    }
}