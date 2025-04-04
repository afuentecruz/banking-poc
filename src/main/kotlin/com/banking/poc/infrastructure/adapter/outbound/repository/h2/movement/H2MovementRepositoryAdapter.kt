package com.banking.poc.infrastructure.adapter.outbound.repository.h2.movement

import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.application.port.outbound.repository.MovementRepositoryPort
import org.springframework.stereotype.Component

@Component
class H2MovementRepositoryAdapter(private val h2MovementRepository: SpringDataH2MovementRepository) : MovementRepositoryPort {
    override fun save(movement: Movement): Movement =
        h2MovementRepository.save(movement.toEntity()).toDomain()

    override fun findAllWalletMovements(walletId: Long): List<Movement> =
        h2MovementRepository.findAllByWalletId(walletId).map { it.toDomain() }
}
