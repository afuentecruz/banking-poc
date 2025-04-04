package com.banking.poc.application.usecase.wallet

import com.banking.poc.domain.dto.wallet.WalletBalanceResponse
import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.exception.UserWalletNotFoundException
import com.banking.poc.domain.exception.WalletNotFoundException
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.service.AmlValidationService
import com.banking.poc.domain.service.UserService
import com.banking.poc.domain.service.WalletService
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WalletDetailsUseCaseTest {

    private var walletService: WalletService = mockk()
    private var userService: UserService = mockk()
    private var amlValidationService: AmlValidationService = mockk()

    private val walletDetailsUseCase =
        WalletDetailsUseCase(walletService, userService, amlValidationService)

    @Test
    fun whenFindWalletsForUserWithNoWallet_shouldThrowException() {
        // given
        val userId: Long = 1L
        val user: User = User.testData()
        every { userService.findUsername(user.username) } returns (user)
        every { walletService.findUserWallets(userId) } throws UserWalletNotFoundException(userId)
        // when
        val result = Assertions.assertThrows(UserWalletNotFoundException::class.java) {
            walletDetailsUseCase.getUserWallets(user.username)
        }
        // then
        Assertions.assertEquals("Wallet for userId $userId not found", result.message)
        verify(exactly = 1) { walletService.findUserWallets(userId) }
    }

    @Test
    fun whenFindWalletsForCreatedUserWallets_shouldReturnTheWallets() {
        // given
        val userId: Long = 1L
        val user: User = User.testData()
        val wallet = Wallet.testData()
        every { userService.findUsername(user.username) } returns (user)
        every { walletService.findUserWallets(userId) } returns (listOf(wallet))
        // when
        val result = walletDetailsUseCase.getUserWallets(user.username)
        // then
        Assertions.assertTrue(result.isNotEmpty())
        Assertions.assertEquals(wallet.id, result.first().id)
        Assertions.assertEquals(wallet.balance.amount, result.first().balance)
        Assertions.assertEquals(wallet.balance.currency.name, result.first().currency)
    }

    @Test
    fun getWalletBalanceFromANotUserWallet_shouldThrowForbiddenWalletUsageException() {
        // given
        val user: User = User.testData()
        val anotherUser: User = User.testData()
        val wallet: Wallet = Wallet.testData(user = anotherUser)

        every {
            amlValidationService.checkWalletOwnership(
                user.username,
                wallet.id!!
            )
        } throws ForbiddenWalletUsageException()

        // when
        val result = Assertions.assertThrows(ForbiddenWalletUsageException::class.java) {
            walletDetailsUseCase.getWalletBalance(user.username, wallet.id!!)
        }

        Assertions.assertEquals("You are not allowed to use this wallet", result.message)
        verify(exactly = 1) { amlValidationService.checkWalletOwnership(user.username, wallet.id!!) }
    }

    @Test
    fun getWalletBalanceFromNotExistingWallet_shouldThrowWalletNotFoundException() {
        // given
        val user: User = User.testData()
        val anotherUser: User = User.testData()
        val wallet: Wallet = Wallet.testData(user = anotherUser)

        every { amlValidationService.checkWalletOwnership(user.username, wallet.id!!) } returns Unit
        every { walletService.findWallet(wallet.id!!) } throws WalletNotFoundException(wallet.id!!)
        // when
        val result = Assertions.assertThrows(WalletNotFoundException::class.java) {
            walletDetailsUseCase.getWalletBalance(user.username, wallet.id!!)
        }

        Assertions.assertEquals("Wallet with id ${wallet.id!!} not found", result.message)
        verify(exactly = 1) { amlValidationService.checkWalletOwnership(user.username, wallet.id!!) }
    }

    @Test
    fun getWalletBalanceFromExistingWallet_shouldReturnsItsBalance() {
        // given
        val user: User = User.testData()
        val anotherUser: User = User.testData()
        val wallet: Wallet = Wallet.testData(user = anotherUser)

        every { amlValidationService.checkWalletOwnership(user.username, wallet.id!!) } returns Unit
        every { walletService.findWallet(wallet.id!!) } returns wallet
        // when
        val result: WalletBalanceResponse = walletDetailsUseCase.getWalletBalance(user.username, wallet.id!!)

        Assertions.assertEquals(wallet.balance.amount, result.balance)
        Assertions.assertEquals(wallet.balance.currency.name, result.currency)
        verify(exactly = 1) { amlValidationService.checkWalletOwnership(user.username, wallet.id!!) }
        verify(exactly = 1) { walletService.findWallet(wallet.id!!) }
    }
}
