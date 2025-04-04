package com.banking.poc.infrastructure.adapter.inbound.usecase.controller

import com.banking.poc.application.port.inbound.usecase.WalletUseCasePort
import com.banking.poc.application.usecase.wallet.WalletCreateUseCase
import com.banking.poc.application.usecase.wallet.WalletDepositUseCase
import com.banking.poc.application.usecase.wallet.WalletDetailsUseCase
import com.banking.poc.domain.dto.wallet.WalletBalanceResponse
import com.banking.poc.domain.dto.wallet.WalletDepositRequest
import com.banking.poc.domain.dto.wallet.WalletResponse
import com.banking.poc.infrastructure.security.util.SecurityUtils
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/wallets")
class WalletControllerAdapter(
    private val createWalletUseCase: WalletCreateUseCase,
    private val walletDetailsUseCase: WalletDetailsUseCase,
    private val walletDepositUseCase: WalletDepositUseCase
) : WalletUseCasePort {

    @PostMapping(path = ["/create"])
    override fun createWallet(): WalletResponse =
        createWalletUseCase.createUserWallet(SecurityUtils.getUsername())


    @GetMapping()
    override fun findUserWallets(): List<WalletResponse> =
        walletDetailsUseCase.getUserWallets(SecurityUtils.getUsername())

    @PostMapping(path = ["/{walletId}/deposit"])
    override fun depositWallet(
        @PathVariable walletId: Long,
        @Valid @RequestBody depositRequest: WalletDepositRequest
    ): WalletResponse =
        walletDepositUseCase.deposit(walletId, depositRequest)

    @GetMapping(path = ["/{walletId}/balance"])
    override fun walletBalance(
        @PathVariable walletId: Long
    ): WalletBalanceResponse =
        walletDetailsUseCase.getWalletBalance(SecurityUtils.getUsername(), walletId)
}

