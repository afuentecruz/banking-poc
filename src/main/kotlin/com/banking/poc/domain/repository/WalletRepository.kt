package com.banking.poc.domain.repository

import com.banking.poc.domain.model.wallet.Wallet

interface WalletRepository {
    fun saveWallet(wallet: Wallet): Wallet
    fun findWallet(walletId: Long): Wallet?
    fun findWalletsByUserId(userId: Long): List<Wallet>
    fun findWalletCurrency(walletId: Long, currency: String): Wallet?
}
