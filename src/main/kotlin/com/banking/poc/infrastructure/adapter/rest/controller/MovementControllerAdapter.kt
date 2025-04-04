package com.banking.poc.infrastructure.adapter.rest.controller

import com.banking.poc.application.port.inbound.rest.MovementRestInbound
import com.banking.poc.application.usecase.movement.WalletMovementUseCase
import com.banking.poc.domain.dto.movement.MovementResponse
import com.banking.poc.infrastructure.security.util.SecurityUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/movements")
class MovementControllerAdapter(
    private val walletMovementUseCase: WalletMovementUseCase
) : MovementRestInbound {

    @GetMapping(path = ["/wallet/{walletId}"])
    override fun findWalletMovements(
        @PathVariable walletId: Long
    ): List<MovementResponse> =
        walletMovementUseCase.getWalletMovements(SecurityUtils.getUsername(), walletId)
}
