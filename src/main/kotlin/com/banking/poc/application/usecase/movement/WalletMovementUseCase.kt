package com.banking.poc.application.usecase.movement

import com.banking.poc.domain.dto.movement.MovementResponse
import com.banking.poc.domain.dto.movement.fromDomain
import com.banking.poc.domain.service.AmlValidationService
import com.banking.poc.domain.service.MovementService

class WalletMovementUseCase(
    private val amlValidationService: AmlValidationService,
    private val movementService: MovementService
) {
    fun getWalletMovements(username: String, walletId: Long): List<MovementResponse> =
        amlValidationService.checkWalletOwnership(username, walletId).run {
            movementService.findWalletMovements(walletId).map { MovementResponse.fromDomain(it) }
        }
}
