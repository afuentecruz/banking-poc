package com.banking.poc.domain.service

import com.banking.poc.application.port.outbound.repository.WalletRepositoryOutbound
import com.banking.poc.domain.exception.UserWalletNotFoundException
import com.banking.poc.domain.exception.WalletCurrencyNotFoundException
import com.banking.poc.domain.exception.WalletNotFoundException
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.addAmount
import com.banking.poc.domain.model.wallet.minusAmount
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WalletServiceTest {

    private var walletRepository: WalletRepositoryOutbound = mockk()

    private val walletService: WalletService = WalletService(walletRepository)

    @Test
    fun whenCreateWalletOfExistingUser_shouldReturnTheCreatedWallet() {
        // given
        val user: User = User.testData()
        val wallet: Wallet =
            Wallet.testData(id = null, balance = Money(amount = BigDecimal.ZERO, currency = MoneyCurrency.EUR))
        val createdWallet: Wallet =
            Wallet.testData(balance = Money(amount = BigDecimal.ZERO, currency = MoneyCurrency.EUR))
        every { walletRepository.saveWallet(wallet) } returns (createdWallet)
        // when
        val result = walletService.createWallet(user)
        // then
        Assertions.assertEquals(wallet.balance.amount, result.balance.amount)
        Assertions.assertEquals(wallet.balance.currency, result.balance.currency)
        Assertions.assertEquals(wallet.user.id, result.user.id)
        Assertions.assertEquals(wallet.user.username, result.user.username)
        Assertions.assertEquals(wallet.user.password, result.user.password)
        Assertions.assertEquals(1, result.id)
    }

    @Test
    fun whenFindWalletsByUserIdOfExistingUser_shouldReturnTheWallets() {
        // given
        val user: User = User.testData()
        val wallet: Wallet = Wallet.testData(user = user)
        every { walletRepository.findWalletsByUserId(user.id!!) } returns (listOf(wallet))
        // when
        val result = walletService.findUserWallets(user.id!!)
        // then
        Assertions.assertTrue(result.isNotEmpty())
        Assertions.assertEquals(wallet.id!!, result.first().id!!)
        Assertions.assertEquals(wallet.balance.amount, result.first().balance.amount)
        Assertions.assertEquals(wallet.balance.currency, result.first().balance.currency)
        Assertions.assertEquals(wallet.user.id, result.first().user.id)
        Assertions.assertEquals(wallet.user.username, result.first().user.username)
        Assertions.assertEquals(wallet.user.password, result.first().user.password)
    }

    @Test
    fun whenFindWalletsByUserIdOfExistingUserButWithNoWallets_shouldThrowException() {
        // given
        val user: User = User.testData()
        every { walletRepository.findWalletsByUserId(user.id!!) } throws (UserWalletNotFoundException(user.id!!))
        // when
        val result =
            Assertions.assertThrows(UserWalletNotFoundException::class.java) { walletService.findUserWallets(user.id!!) }
        // then
        Assertions.assertEquals("Wallet for userId ${user.id} not found", result.message)
    }

    @Test
    fun whenFindWalletCurrencyOfANonExistingWalletCurrencyPair_shouldThrowWalletCurrencyNotFoundException() {
        // given
        val walletId: Long = 1L
        val currency: String = MoneyCurrency.EUR.name
        every { walletRepository.findWalletCurrency(walletId, currency) } throws WalletCurrencyNotFoundException(
            walletId, currency
        )
        // when
        val result = Assertions.assertThrows(WalletCurrencyNotFoundException::class.java) {
            walletService.findWalletCurrency(
                walletId, currency
            )
        }
        // then
        Assertions.assertEquals("Wallet with id $walletId and currency $currency not found", result.message)
    }

    @Test
    fun whenFindWalletCurrencyOfAnExistingWalletCurrencyPair_shouldReturnTheWallet() {
        // given
        val wallet: Wallet = Wallet.testData()
        every { walletRepository.findWalletCurrency(wallet.id!!, wallet.balance.currency.name) } returns wallet
        // when
        val result = walletService.findWalletCurrency(
            wallet.id!!, wallet.balance.currency.name
        )
        // then
        Assertions.assertEquals(wallet.id, result.id)
        Assertions.assertEquals(wallet.balance, result.balance)
        Assertions.assertEquals(wallet.user, result.user)
    }

    @Test
    fun whenFindWalletOfANonExistingWallet_shouldThrowWalletNotFoundException() {
        // given
        val walletId: Long = 1L
        every { walletRepository.findWallet(walletId) } throws WalletNotFoundException(walletId)
        // when
        val result = Assertions.assertThrows(WalletNotFoundException::class.java) {
            walletService.findWallet(walletId)
        }
        // then
        Assertions.assertEquals("Wallet with id $walletId not found", result.message)
    }

    @Test
    fun whenFindWalletOfAnExistingWallet_shouldReturnTheWallet() {
        // given
        val wallet: Wallet = Wallet.testData()
        every { walletRepository.findWallet(wallet.id!!) } returns wallet
        // when
        val result = walletService.findWallet(wallet.id!!)
        // then
        Assertions.assertEquals(wallet.id, result.id)
        Assertions.assertEquals(wallet.balance, result.balance)
        Assertions.assertEquals(wallet.user, result.user)
    }

    @Test
    fun whenWalletDeposit_shouldSaveTheDepositedWallet() {
        // given
        val amount = BigDecimal.TEN
        val wallet: Wallet = Wallet.testData()
        val walletDeposit = wallet.addAmount(amount)
        every { walletRepository.saveWallet(walletDeposit) } returns walletDeposit
        // when
        val result = walletService.deposit(wallet, amount)
        // then
        Assertions.assertEquals(walletDeposit.id, result.id)
        Assertions.assertEquals(walletDeposit.user, result.user)
        Assertions.assertEquals(walletDeposit.balance, result.balance)
        verify { walletRepository.saveWallet(walletDeposit) }
    }

    @Test
    fun whenWalletWithdraw_shouldSaveTheWithdrawedWallet() {
        // given
        val amount = BigDecimal.TEN
        val wallet: Wallet = Wallet.testData()
        val walletWithdraw = wallet.minusAmount(amount)
        every { walletRepository.saveWallet(walletWithdraw) } returns walletWithdraw
        // when
        val result = walletService.withdraw(wallet, amount)
        // then
        Assertions.assertEquals(walletWithdraw.id, result.id)
        Assertions.assertEquals(walletWithdraw.user, result.user)
        Assertions.assertEquals(walletWithdraw.balance, result.balance)
        verify { walletRepository.saveWallet(walletWithdraw) }
    }

}
