package com.banking.poc.application.usecase.wallet

import com.banking.poc.application.service.MovementService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.infrastructure.rest.wallet.dto.WalletDepositRequest
import com.banking.poc.infrastructure.rest.wallet.dto.WalletResponse
import com.banking.poc.infrastructure.rest.wallet.dto.fromDomain

class WalletDepositUseCase(
    private val walletService: WalletService,
    private val movementService: MovementService
) {
    fun deposit(walletId: Long, walletDepositRequest: WalletDepositRequest): WalletResponse {
        walletService.findWalletCurrency(walletId, walletDepositRequest.currency).apply {
            movementService.doMovement(
                this,
                Money(walletDepositRequest.amount, this.balance.currency),
                MovementType.DEPOSIT
            ).also {
                walletService.deposit(this, walletDepositRequest.amount).let {
                    return WalletResponse.fromDomain(it)
                }
            }
        }
    }
}
