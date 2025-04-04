package com.banking.poc.infrastructure.adapter.outbound.repository.h2.movement

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataH2MovementRepository : JpaRepository<MovementEntity, Long> {
    fun findAllByWalletId(walletId: Long): List<MovementEntity>
}
