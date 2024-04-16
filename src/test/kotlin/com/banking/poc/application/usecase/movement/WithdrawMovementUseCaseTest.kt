package com.banking.poc.application.usecase.movement

import com.banking.poc.application.service.MovementService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.minusAmount
import com.banking.poc.utils.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WithdrawMovementUseCaseTest {

    private var walletService: WalletService = mockk()
    private var movementService: MovementService = mockk()

    private val withdrawMovementUseCase: WithdrawMovementUseCase =
        WithdrawMovementUseCase(walletService, movementService)

    @Test
    fun withdrawMovement_shouldCallWalletServiceAndMovementService() {
        // given
        val money = Money(amount = BigDecimal.ONE, currency = MoneyCurrency.EUR)
        val wallet = Wallet.testData(balance = Money(amount = BigDecimal.TEN, currency = MoneyCurrency.EUR))
        val withdrawWallet = wallet.minusAmount(money.amount)
        val movement = Movement.testData(wallet = withdrawWallet, type = MovementType.WITHDRAW, money = money)
        every { walletService.withdraw(wallet, money.amount) } returns withdrawWallet
        every { movementService.doMovement(wallet, money, MovementType.WITHDRAW) } returns movement

        // when
        val result = withdrawMovementUseCase.withdrawMovement(wallet, money)
        // then
        Assertions.assertNotNull(result.movementId)
        Assertions.assertEquals(wallet, result.wallet)
        Assertions.assertEquals(money, result.money)
        Assertions.assertEquals(MovementType.WITHDRAW, result.type)
        Assertions.assertNotNull(result.creationDate)
        verify {
            walletService.withdraw(wallet, money.amount)
        }
    }
}
