package com.banking.poc.domain.model.movement

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.wallet.Wallet
import java.time.LocalDateTime

data class Movement(
    val movementId: Long? = null,
    val type: MovementType,
    val money: Money,
    val wallet: Wallet,
    val creationDate: LocalDateTime = LocalDateTime.now()
) {
    companion object
}
