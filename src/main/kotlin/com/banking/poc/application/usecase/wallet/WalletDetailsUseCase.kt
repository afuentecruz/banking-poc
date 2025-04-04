package com.banking.poc.application.usecase.wallet

import com.banking.poc.domain.dto.wallet.WalletBalanceResponse
import com.banking.poc.domain.dto.wallet.WalletResponse
import com.banking.poc.domain.dto.wallet.fromDomain
import com.banking.poc.domain.service.AmlValidationService
import com.banking.poc.domain.service.UserService
import com.banking.poc.domain.service.WalletService

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
