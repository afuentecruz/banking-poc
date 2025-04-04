package com.banking.poc.application.usecase.movement

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.service.MovementService
import com.banking.poc.domain.service.WalletService

class WithdrawMovementUseCase(
    private val walletService: WalletService,
    private val movementService: MovementService
) {
    fun withdrawMovement(wallet: Wallet, money: Money): Movement =
        walletService.withdraw(wallet, money.amount).let {
            movementService.doMovement(it, money, MovementType.WITHDRAW)
        }
}
