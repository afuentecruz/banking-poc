package com.banking.poc.infrastructure.repository.h2.movement

import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.repository.MovementRepository
import org.springframework.stereotype.Component

@Component
class H2MovementRepository(private val h2MovementRepository: SpringDataH2MovementRepository) : MovementRepository {
    override fun save(movement: Movement): Movement =
        h2MovementRepository.save(movement.toEntity()).toDomain()

    override fun findAllWalletMovements(walletId: Long): List<Movement> =
        h2MovementRepository.findAllByWalletId(walletId).map { it.toDomain() }
}
