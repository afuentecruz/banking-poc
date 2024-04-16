package com.banking.poc.application.usecase.movement

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.MovementService
import com.banking.poc.infrastructure.rest.movement.dto.WalletMovementResponse
import com.banking.poc.infrastructure.rest.movement.dto.fromDomain

class WalletMovementUseCase(
    private val amlValidationService: AmlValidationService,
    private val movementService: MovementService
) {
    fun getWalletMovements(username: String, walletId: Long): List<WalletMovementResponse> =
        amlValidationService.checkWalletOwnership(username, walletId).run {
            movementService.findWalletMovements(walletId).map { WalletMovementResponse.fromDomain(it) }
        }
}
