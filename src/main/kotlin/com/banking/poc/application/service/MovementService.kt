package com.banking.poc.application.service

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet

interface MovementService {
    fun doMovement(wallet: Wallet, money: Money, type: MovementType): Movement
    fun findWalletMovements(walletId: Long): List<Movement>
}
