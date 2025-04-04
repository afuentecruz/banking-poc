package com.banking.poc.infrastructure.adapter.outbound.repository.h2.movement

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.infrastructure.adapter.outbound.repository.h2.wallet.toDomain
import com.banking.poc.infrastructure.adapter.outbound.repository.h2.wallet.toEntity

fun Movement.toEntity(): MovementEntity =
    MovementEntity(
        id = this.movementId,
        movementType = this.type.name,
        amount = this.money.amount,
        currency = this.money.currency.name,
        wallet = this.wallet.toEntity(),
        createdAt = this.creationDate
    )

fun MovementEntity.toDomain(): Movement = Movement(
    movementId = this.id,
    type = MovementType.valueOf(this.movementType),
    money = Money(amount = this.amount, currency = MoneyCurrency.valueOf(this.currency)),
    wallet = this.wallet.toDomain(),
    creationDate = this.createdAt!!
)
