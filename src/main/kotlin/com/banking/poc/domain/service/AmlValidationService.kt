package com.banking.poc.domain.service

import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.exception.InsufficientFundsException
import com.banking.poc.domain.exception.SameWalletsException
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.hasEnoughBalance
import java.math.BigDecimal

class AmlValidationService(
    private val walletService: WalletService,
    private val userService: UserService
) {

    fun checkWalletOwnership(username: String, walletId: Long) =
        userService.findUsername(username).let { user ->
            walletService.findUserWallets(user.id!!).let { userWallets ->
                if (walletId !in userWallets.map { it.id }) throw ForbiddenWalletUsageException()
            }
        }

    fun checkWalletBalance(wallet: Wallet, amount: BigDecimal) =
        wallet.hasEnoughBalance(amount).let { hasEnough -> if (!hasEnough) throw InsufficientFundsException() }

    fun checkWalletsAreNotTheSame(origin: Wallet, destination: Wallet) {
        if (origin.id!! == destination.id!!) throw SameWalletsException()
    }
}
