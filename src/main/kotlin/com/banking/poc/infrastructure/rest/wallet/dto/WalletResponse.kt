package com.banking.poc.infrastructure.rest.wallet.dto

import com.banking.poc.domain.model.wallet.Wallet
import java.math.BigDecimal

data class WalletResponse(
    val id: Long, val balance: BigDecimal, val currency: String
) {
    companion object
}

fun WalletResponse.Companion.fromDomain(wallet: Wallet) =
    WalletResponse(id = wallet.id!!, balance = wallet.balance.amount, currency = wallet.balance.currency.name)
