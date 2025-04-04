package com.banking.poc.domain.service

import com.banking.poc.application.port.outbound.repository.MovementRepositoryPort
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet

class MovementService(
    private val movementRepository: MovementRepositoryPort
) {

    fun doMovement(wallet: Wallet, money: Money, type: MovementType): Movement =
        Movement(type = type, money = money, wallet = wallet)
            .let { movementRepository.save(it) }

    fun findWalletMovements(walletId: Long): List<Movement> =
        movementRepository.findAllWalletMovements(walletId)

}
