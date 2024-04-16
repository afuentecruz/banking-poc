package com.banking.poc.domain.service

import com.banking.poc.application.service.MovementService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.repository.MovementRepository

class MovementServiceImpl(
    private val movementRepository: MovementRepository
) : MovementService {

    override fun doMovement(wallet: Wallet, money: Money, type: MovementType): Movement =
        Movement(type = type, money = money, wallet = wallet)
            .let { movementRepository.save(it) }

    override fun findWalletMovements(walletId: Long): List<Movement> =
        movementRepository.findAllWalletMovements(walletId)

}
