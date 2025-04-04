package com.banking.poc.domain.service

import com.banking.poc.application.port.outbound.repository.MovementRepositoryPort
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MovementServiceTest {

    private var movementRepository: MovementRepositoryPort = mockk()
    private val movementService: MovementService = MovementService(movementRepository)

    @Test
    fun whenDoMovement_shouldCallMovementRepository() {
        // given
        val wallet = Wallet.testData()
        val money = Money.testData()
        val type = MovementType.DEPOSIT
        val movement = Movement(type = type, money = money, wallet = wallet)
        val savedMovement = movement.copy(movementId = 1L)
        every { movementRepository.save(any()) } returns savedMovement
        // when
        val result = movementService.doMovement(wallet, money, type)
        // then
        Assertions.assertEquals(savedMovement.movementId, result.movementId)
        Assertions.assertEquals(savedMovement.type, result.type)
        Assertions.assertEquals(savedMovement.wallet, result.wallet)
        Assertions.assertEquals(savedMovement.money, result.money)
        verify { movementRepository.save(any()) }
    }

    @Test
    fun whenFindWalletWithNoMovements_shouldReturnEmptyList() {
        // given
        val walletId: Long = 1L
        every { movementRepository.findAllWalletMovements(walletId) } returns emptyList()
        // when
        val result = movementService.findWalletMovements(walletId)
        // then
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun whenFindWalletWithMovements_shouldReturnTheMovementsList() {
        // given
        val walletId: Long = 1L
        val movements: List<Movement> = listOf(Movement.testData(type = MovementType.DEPOSIT))
        every { movementRepository.findAllWalletMovements(walletId) } returns movements
        // when
        val result = movementService.findWalletMovements(walletId)
        // then
        Assertions.assertTrue(result.isNotEmpty())
        Assertions.assertEquals(movements.first().movementId, result.first().movementId)
        Assertions.assertEquals(movements.first().wallet, result.first().wallet)
        Assertions.assertEquals(movements.first().money, result.first().money)
        Assertions.assertEquals(movements.first().type, result.first().type)
        Assertions.assertEquals(movements.first().creationDate, result.first().creationDate)
    }

}
