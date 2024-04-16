package com.banking.poc.application.usecase.movement

import com.banking.poc.application.service.MovementService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet

class DepositMovementUseCase(
    private val walletService: WalletService, private val movementService: MovementService
) {
    fun depositMovement(wallet: Wallet, money: Money): Movement = walletService.deposit(wallet, money.amount).let {
        movementService.doMovement(wallet, money, MovementType.DEPOSIT)
    }
}
