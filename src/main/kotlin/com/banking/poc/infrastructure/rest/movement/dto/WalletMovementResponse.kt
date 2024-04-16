package com.banking.poc.infrastructure.rest.movement.dto

import com.banking.poc.domain.model.movement.Movement
import java.math.BigDecimal
import java.time.LocalDateTime

data class WalletMovementResponse(
    val type: String,
    val amount: BigDecimal,
    val currency: String,
    val creationDate: LocalDateTime
) {
    companion object
}

fun WalletMovementResponse.Companion.fromDomain(movement: Movement): WalletMovementResponse =
    WalletMovementResponse(
        type = movement.type.name,
        amount = movement.money.amount,
        currency = movement.money.currency.name,
        creationDate = movement.creationDate
    )
