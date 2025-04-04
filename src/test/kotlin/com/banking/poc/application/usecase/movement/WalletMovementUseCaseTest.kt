package com.banking.poc.application.usecase.movement

import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.service.AmlValidationService
import com.banking.poc.domain.service.MovementService
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WalletMovementUseCaseTest {

    private var amlValidationService: AmlValidationService = mockk()
    private var movementService: MovementService = mockk()

    private val walletMovementUseCase: WalletMovementUseCase =
        WalletMovementUseCase(amlValidationService, movementService)

    @Test
    fun whenGetWalletMovementsOfNotUserWallet_shouldThrowForbiddenWalletUsageException() {
        // given
        val username = "username"
        val walletId = 1L
        every { amlValidationService.checkWalletOwnership(username, walletId) } throws ForbiddenWalletUsageException()
        // when
        val result = Assertions.assertThrows(ForbiddenWalletUsageException::class.java) {
            walletMovementUseCase.getWalletMovements(username, walletId)
        }
        // then
        Assertions.assertEquals("You are not allowed to use this wallet", result.message)
        verify { amlValidationService.checkWalletOwnership(username, walletId) }
    }

    @Test
    fun whenGetWalletMovements_shouldReturnMovementsList() {
        // given
        val username = "username"
        val walletId = 1L
        val movements = listOf(Movement.testData(type = MovementType.DEPOSIT))
        every { amlValidationService.checkWalletOwnership(username, walletId) } returns Unit
        every { movementService.findWalletMovements(walletId) } returns movements
        // when
        val result = walletMovementUseCase.getWalletMovements(username, walletId)

        // then
        Assertions.assertTrue(result.isNotEmpty())
        Assertions.assertEquals(movements.first().money.amount, result.first().amount)
        Assertions.assertEquals(movements.first().type.name, result.first().type)
        Assertions.assertEquals(movements.first().money.currency.name, result.first().currency)
        Assertions.assertNotNull(result.first().creationDate)
        verify { amlValidationService.checkWalletOwnership(username, walletId) }
    }
}
