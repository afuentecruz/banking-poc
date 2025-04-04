package com.banking.poc.infrastructure.adapter.outbound.repository.h2.wallet

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.infrastructure.adapter.outbound.repository.h2.user.toDomain
import com.banking.poc.infrastructure.adapter.outbound.repository.h2.user.toEntity

fun Wallet.toEntity(): WalletEntity =
    WalletEntity(
        id = this.id,
        amount = this.balance.amount,
        currency = this.balance.currency.name,
        user = this.user.toEntity()
    )

fun WalletEntity.toDomain(): Wallet = Wallet(
    id = this.id,
    user = this.user.toDomain(),
    balance = Money(amount = this.amount, currency = MoneyCurrency.valueOf(this.currency))
)
