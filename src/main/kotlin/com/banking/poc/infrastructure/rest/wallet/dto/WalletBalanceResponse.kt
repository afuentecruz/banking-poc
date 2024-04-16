package com.banking.poc.infrastructure.rest.wallet.dto

import com.banking.poc.domain.model.wallet.Wallet
import java.math.BigDecimal

data class WalletBalanceResponse(
    val balance: BigDecimal, val currency: String
) {
    companion object
}

fun WalletBalanceResponse.Companion.fromDomain(wallet: Wallet) =
    WalletBalanceResponse(balance = wallet.balance.amount, currency = wallet.balance.currency.name)
