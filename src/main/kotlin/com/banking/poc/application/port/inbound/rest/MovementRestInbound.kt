package com.banking.poc.application.port.inbound.rest

import com.banking.poc.domain.dto.movement.MovementResponse

interface MovementRestInbound {
    fun findWalletMovements(walletId: Long): List<MovementResponse>;
}
