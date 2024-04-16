package com.banking.poc.application.service

import com.banking.poc.domain.model.wallet.Wallet
import java.math.BigDecimal

interface AmlValidationService {
    fun checkWalletOwnership(username: String, walletId: Long)
    fun checkWalletBalance(wallet: Wallet, amount: BigDecimal)
    fun checkWalletsAreNotTheSame(origin: Wallet, destination: Wallet)
}
