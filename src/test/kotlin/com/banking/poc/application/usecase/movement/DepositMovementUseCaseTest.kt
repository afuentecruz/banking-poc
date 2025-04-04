package com.banking.poc.application.usecase.movement

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.addAmount
import com.banking.poc.domain.service.MovementService
import com.banking.poc.domain.service.WalletService
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DepositMovementUseCaseTest {

    private var walletService: WalletService = mockk()
    private var movementService: MovementService = mockk()

    private val depositMovementUseCase: DepositMovementUseCase =
        DepositMovementUseCase(walletService, movementService)

    @Test
    fun depositMovement_shouldCallWalletServiceAndMovementService() {
        // given
        val money = Money(amount = BigDecimal.TEN, currency = MoneyCurrency.EUR)
        val wallet = Wallet.testData()
        val depositedWallet = wallet.addAmount(money.amount)
        val movement = Movement.testData(wallet = depositedWallet, type = MovementType.DEPOSIT)
        every { walletService.deposit(wallet, money.amount) } returns depositedWallet
        every { movementService.doMovement(wallet, money, MovementType.DEPOSIT) } returns movement

        // when
        val result = depositMovementUseCase.depositMovement(wallet, money)
        // then
        Assertions.assertNotNull(result.movementId)
        Assertions.assertEquals(wallet, result.wallet)
        Assertions.assertEquals(money, result.money)
        Assertions.assertEquals(MovementType.DEPOSIT, result.type)
        Assertions.assertNotNull(result.creationDate)
        verify {
            walletService.deposit(wallet, money.amount)
        }
    }
}
