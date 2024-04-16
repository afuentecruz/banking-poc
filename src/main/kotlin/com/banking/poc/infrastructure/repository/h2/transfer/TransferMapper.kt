package com.banking.poc.infrastructure.repository.h2.transfer

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.infrastructure.repository.h2.wallet.toDomain
import com.banking.poc.infrastructure.repository.h2.wallet.toEntity

fun Transfer.toEntity(): TransferEntity =
    TransferEntity(
        id = this.transferId,
        status = this.status.name,
        amount = this.money.amount,
        currency = this.money.currency.name,
        origin = this.origin.toEntity(),
        destination = this.destination.toEntity()
    )

fun TransferEntity.toDomain(): Transfer =
    Transfer(
        transferId = this.id,
        money = Money(amount = this.amount, currency = MoneyCurrency.valueOf(this.currency)),
        origin = this.origin.toDomain(),
        destination = this.destination.toDomain(),
        status = TransferStatus.valueOf(this.status)
    )
