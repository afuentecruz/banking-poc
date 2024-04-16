package com.banking.poc.domain.repository

import com.banking.poc.domain.model.movement.Movement

interface MovementRepository {
    fun save(movement: Movement): Movement
    fun findAllWalletMovements(walletId: Long): List<Movement>
}
