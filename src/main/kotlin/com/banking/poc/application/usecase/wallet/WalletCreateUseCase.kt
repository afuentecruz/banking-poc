package com.banking.poc.application.usecase.wallet

import com.banking.poc.application.service.UserService
import com.banking.poc.application.service.WalletService
import com.banking.poc.infrastructure.rest.wallet.dto.WalletResponse
import com.banking.poc.infrastructure.rest.wallet.dto.fromDomain

class WalletCreateUseCase(private val userService: UserService, private val walletService: WalletService) {
    fun createUserWallet(username: String): WalletResponse = userService.findUsername(username).let { user ->
        walletService.createWallet(user).let {
            WalletResponse.fromDomain(it)
        }
    }
}
