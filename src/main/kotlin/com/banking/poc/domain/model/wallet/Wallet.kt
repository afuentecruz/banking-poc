package com.banking.poc.domain.model.wallet

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.user.User
import java.math.BigDecimal

data class Wallet(val id: Long? = null, val user: User, val balance: Money) {
    companion object
}

fun Wallet.addAmount(amount: BigDecimal): Wallet {
    this.balance.amount = this.balance.amount.add(amount)
    return this
}

fun Wallet.minusAmount(amount: BigDecimal): Wallet {
    this.balance.amount = this.balance.amount.minus(amount)
    return this
}

fun Wallet.hasEnoughBalance(amount: BigDecimal): Boolean = this.balance.amount.compareTo(amount) >= 0
