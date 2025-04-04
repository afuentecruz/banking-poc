package com.banking.poc.application.usecase.wallet

import com.banking.poc.domain.dto.wallet.WalletResponse
import com.banking.poc.domain.dto.wallet.fromDomain
import com.banking.poc.domain.service.UserService
import com.banking.poc.domain.service.WalletService

class WalletCreateUseCase(private val userService: UserService, private val walletService: WalletService) {
    fun createUserWallet(username: String): WalletResponse = userService.findUsername(username).let { user ->
        walletService.createWallet(user).let {
            WalletResponse.fromDomain(it)
        }
    }
}
