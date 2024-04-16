package com.banking.poc.domain.service

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.UserService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.exception.InsufficientFundsException
import com.banking.poc.domain.exception.SameWalletsException
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.model.wallet.hasEnoughBalance
import java.math.BigDecimal

class AmlValidationServiceImpl(
    private val walletService: WalletService,
    private val userService: UserService
) : AmlValidationService {

    override fun checkWalletOwnership(username: String, walletId: Long) =
        userService.findUsername(username).let { user ->
            walletService.findUserWallets(user.id!!).let { userWallets ->
                if (walletId !in userWallets.map { it.id }) throw ForbiddenWalletUsageException()
            }
        }

    override fun checkWalletBalance(wallet: Wallet, amount: BigDecimal) =
        wallet.hasEnoughBalance(amount).let { hasEnough -> if (!hasEnough) throw InsufficientFundsException() }

    override fun checkWalletsAreNotTheSame(origin: Wallet, destination: Wallet) {
        if (origin.id!! == destination.id!!) throw SameWalletsException()
    }
}
