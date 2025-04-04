package com.banking.poc.domain.dto.movement

import com.banking.poc.domain.model.movement.Movement
import java.math.BigDecimal
import java.time.LocalDateTime

data class MovementResponse(
    val type: String,
    val amount: BigDecimal,
    val currency: String,
    val creationDate: LocalDateTime
) {
    companion object
}

fun MovementResponse.Companion.fromDomain(movement: Movement): MovementResponse =
    MovementResponse(
        type = movement.type.name,
        amount = movement.money.amount,
        currency = movement.money.currency.name,
        creationDate = movement.creationDate
    )
