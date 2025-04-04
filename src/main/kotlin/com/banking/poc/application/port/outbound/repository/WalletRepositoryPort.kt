package com.banking.poc.application.port.outbound.repository

import com.banking.poc.domain.model.wallet.Wallet

interface WalletRepositoryPort {
    fun saveWallet(wallet: Wallet): Wallet
    fun findWallet(walletId: Long): Wallet?
    fun findWalletsByUserId(userId: Long): List<Wallet>
    fun findWalletCurrency(walletId: Long, currency: String): Wallet?
}
