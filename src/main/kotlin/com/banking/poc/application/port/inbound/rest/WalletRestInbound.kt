package com.banking.poc.application.port.inbound.rest

import com.banking.poc.domain.dto.wallet.WalletBalanceResponse
import com.banking.poc.domain.dto.wallet.WalletDepositRequest
import com.banking.poc.domain.dto.wallet.WalletResponse

interface WalletRestInbound {
    fun createWallet(): WalletResponse
    fun findUserWallets(): List<WalletResponse>
    fun depositWallet(walletId: Long, depositRequest: WalletDepositRequest): WalletResponse
    fun walletBalance(walletId: Long): WalletBalanceResponse
}