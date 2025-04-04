package com.banking.poc.application.port.inbound.usecase

import com.banking.poc.domain.dto.movement.MovementResponse

interface MovementUseCasePort {
    fun findWalletMovements(walletId: Long): List<MovementResponse>;
}
