package com.banking.poc.application.usecase.wallet

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.UserService
import com.banking.poc.application.service.WalletService
import com.banking.poc.infrastructure.rest.wallet.dto.WalletBalanceResponse
import com.banking.poc.infrastructure.rest.wallet.dto.WalletResponse
import com.banking.poc.infrastructure.rest.wallet.dto.fromDomain

class WalletDetailsUseCase(
    private val walletService: WalletService,
    private val userService: UserService,
    private val amlValidationService: AmlValidationService
) {
    fun getUserWallets(username: String): List<WalletResponse> = userService.findUsername(username).let { user ->
        walletService.findUserWallets(user.id!!).map { WalletResponse.fromDomain(it) }
    }

    fun getWalletBalance(username: String, walletId: Long): WalletBalanceResponse =
        amlValidationService.checkWalletOwnership(username, walletId).run {
            walletService.findWallet(walletId).let {
                WalletBalanceResponse.fromDomain(it)
            }
        }
}
