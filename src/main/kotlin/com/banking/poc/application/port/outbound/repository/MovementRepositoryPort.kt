package com.banking.poc.application.port.outbound.repository

import com.banking.poc.domain.model.movement.Movement

interface MovementRepositoryPort {
    fun save(movement: Movement): Movement
    fun findAllWalletMovements(walletId: Long): List<Movement>
}
