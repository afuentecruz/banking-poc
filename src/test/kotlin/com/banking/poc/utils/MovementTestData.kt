package com.banking.poc.utils

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import java.time.LocalDateTime

fun Movement.Companion.testData(
    movementId: Long? = 1L,
    type: MovementType,
    money: Money = Money.testData(),
    wallet: Wallet = Wallet.testData(),
): Movement = Movement(
    movementId = movementId,
    type = type,
    money = money,
    wallet = wallet,
    creationDate = LocalDateTime.now()
)
