package com.banking.poc.application.port.inbound.usecase

import com.banking.poc.domain.dto.wallet.WalletBalanceResponse
import com.banking.poc.domain.dto.wallet.WalletDepositRequest
import com.banking.poc.domain.dto.wallet.WalletResponse

interface WalletUseCasePort {
    fun createWallet(): WalletResponse
    fun findUserWallets(): List<WalletResponse>
    fun depositWallet(walletId: Long, depositRequest: WalletDepositRequest): WalletResponse
    fun walletBalance(walletId: Long): WalletBalanceResponse
}