package com.banking.poc.domain.service

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.UserService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.exception.InsufficientFundsException
import com.banking.poc.domain.exception.SameWalletsException
import com.banking.poc.domain.exception.UsernameNotFoundException
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.utils.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AmlValidationServiceTest {

    private var walletService: WalletService = mockk()
    private var userService: UserService = mockk()

    private val amlValidationService: AmlValidationService =
        AmlValidationServiceImpl(walletService, userService)

    @Test
    fun whenCheckWalletOwnershipOfANonExistingUsername_shouldThrowUsernameNotFoundException() {
        // given
        val username: String = "username"
        val walletId: Long = 1L
        every { userService.findUsername(username) } throws UsernameNotFoundException(username)
        // when
        val result = Assertions.assertThrows(UsernameNotFoundException::class.java) {
            amlValidationService.checkWalletOwnership(username, walletId)
        }
        // then
        Assertions.assertEquals("User with username $username not found", result.message)
        verify { userService.findUsername(username) }
    }

    @Test
    fun whenCheckWalletOwnershipOfANonExistingWallet_shouldThrowWalletNotFoundException() {
        // given
        val user = User.testData(userId = 1L)
        val wallet = Wallet.testData(user = user)
        val otherWalletId = 2L
        every { userService.findUsername(user.username) } returns user
        every { walletService.findUserWallets(user.id!!) } returns listOf(wallet)
        // when
        val result = Assertions.assertThrows(ForbiddenWalletUsageException::class.java) {
            amlValidationService.checkWalletOwnership(user.username, otherWalletId)
        }
        // then
        Assertions.assertEquals("You are not allowed to use this wallet", result.message)
        verify { userService.findUsername(user.username) }
        verify { walletService.findUserWallets(user.id!!) }
    }

    @Test
    fun whenCheckWalletOwnershipOfAProperlyUserWallet_doNothing() {
        // given
        val user = User.testData(userId = 1L)
        val wallet = Wallet.testData(user = user)
        every { userService.findUsername(user.username) } returns user
        every { walletService.findUserWallets(user.id!!) } returns listOf(wallet)
        // when
        amlValidationService.checkWalletOwnership(user.username, wallet.id!!)
        // then
        verify { userService.findUsername(user.username) }
        verify { walletService.findUserWallets(user.id!!) }
    }

    @Test
    fun whenCheckWalletBalanceOfAGreaterAmount_shouldThrowInsufficientFundsException() {
        // given
        val wallet = Wallet.testData(balance = Money(amount = BigDecimal.ONE, currency = MoneyCurrency.EUR))
        val amount = BigDecimal.TEN
        // when
        val result = Assertions.assertThrows(InsufficientFundsException::class.java) {
            amlValidationService.checkWalletBalance(wallet, amount)
        }
        // then
        Assertions.assertEquals("Wallet hasn't had enough funds to perform this operation", result.message)
    }

    @Test
    fun whenCheckWalletBalanceOfAValidAmount_doNothing() {
        // given
        val wallet = Wallet.testData(balance = Money(amount = BigDecimal.TEN, currency = MoneyCurrency.EUR))
        val amount = BigDecimal.ONE
        // when
        amlValidationService.checkWalletBalance(wallet, amount)
    }

    @Test
    fun whenCheckWalletsAreTheSameForSameWallets_shouldThrowSameWalletsException() {
        // given
        val wallet = Wallet.testData()
        // when
        val result = Assertions.assertThrows(SameWalletsException::class.java) {
            amlValidationService.checkWalletsAreNotTheSame(wallet, wallet)
        }
        // then
        Assertions.assertEquals("Origin and destination Wallet cannot be the same", result.message)
    }

    @Test
    fun whenCheckWalletsAreTheSameForDistinctWallets_doNothing() {
        // given
        val walletA = Wallet.testData(id = 1L)
        val walletB = Wallet.testData(id = 2L)
        // when
        amlValidationService.checkWalletsAreNotTheSame(walletA, walletB)
    }
}
