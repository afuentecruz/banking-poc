package com.banking.poc.domain.service

import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.exception.UserWalletNotFoundException
import com.banking.poc.domain.exception.WalletCurrencyNotFoundException
import com.banking.poc.domain.exception.WalletNotFoundException
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.addAmount
import com.banking.poc.domain.model.wallet.minusAmount
import com.banking.poc.domain.repository.WalletRepository
import java.math.BigDecimal

class WalletServiceImpl(
    private val walletRepository: WalletRepository
) : WalletService {

    override fun createWallet(user: User): Wallet = walletRepository.saveWallet(
        Wallet(
            user = user,
            balance = Money(amount = BigDecimal.ZERO, currency = MoneyCurrency.EUR)
        )
    )

    override fun findWallet(walletId: Long): Wallet =
        walletRepository.findWallet(walletId) ?: throw WalletNotFoundException(walletId)

    override fun findUserWallets(userId: Long): List<Wallet> =
        walletRepository.findWalletsByUserId(userId).ifEmpty { throw UserWalletNotFoundException(userId) }


    override fun findWalletCurrency(walletId: Long, currency: String): Wallet =
        walletRepository.findWalletCurrency(walletId, currency)
            ?: throw WalletCurrencyNotFoundException(walletId, currency)

    override fun deposit(wallet: Wallet, amount: BigDecimal): Wallet =
        wallet.addAmount(amount).apply { walletRepository.saveWallet(this) }

    override fun withdraw(wallet: Wallet, amount: BigDecimal): Wallet =
        wallet.minusAmount(amount).apply { walletRepository.saveWallet(this) }

}
