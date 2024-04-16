package com.banking.poc.application.service

import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import java.math.BigDecimal

interface WalletService {
    fun createWallet(user: User): Wallet
    fun findWallet(walletId: Long): Wallet
    fun findUserWallets(userId: Long): List<Wallet>
    fun findWalletCurrency(walletId: Long, currency: String): Wallet
    fun deposit(wallet: Wallet, amount: BigDecimal): Wallet
    fun withdraw(wallet: Wallet, amount: BigDecimal): Wallet
}
