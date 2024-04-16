package com.banking.poc.application.usecase.transfer

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.TransferService
import com.banking.poc.application.service.WalletService
import com.banking.poc.application.usecase.movement.WithdrawMovementUseCase
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.infrastructure.rest.transfer.dto.TransferRequest

class InternalTransferUseCase(
    private val withdrawMovementUseCase: WithdrawMovementUseCase,
    private val depositMovementUseCase: com.banking.poc.application.usecase.movement.DepositMovementUseCase,
    private val walletService: WalletService,
    private val transferService: TransferService,
    private val amlValidationService: AmlValidationService
) {

    fun internalTransfer(username: String, transferRequest: TransferRequest) {
        val originWallet: Wallet =
            walletService.findWalletCurrency(transferRequest.from, transferRequest.currency)
        val destinationWallet: Wallet =
            walletService.findWalletCurrency(transferRequest.to, transferRequest.currency)

        validateInternalTransfer(username, originWallet, destinationWallet, transferRequest)

        buildTransferMoney(transferRequest).let { transferMoney ->
            transferService.doTransfer(originWallet, destinationWallet, transferMoney).let { transfer ->
                if (TransferStatus.COMPLETED == transfer.status) {
                    withdrawMovementUseCase.withdrawMovement(originWallet, transferMoney)
                    depositMovementUseCase.depositMovement(destinationWallet, transferMoney)
                }
            }
        }
    }

    private fun buildTransferMoney(transferRequest: TransferRequest) = Money(
        amount = transferRequest.amount, currency = MoneyCurrency.valueOf(transferRequest.currency)
    )

    private fun validateInternalTransfer(
        username: String,
        originWallet: Wallet,
        destinationWallet: Wallet,
        transferRequest: TransferRequest
    ) {
        amlValidationService.checkWalletOwnership(username, originWallet.id!!)
        amlValidationService.checkWalletBalance(originWallet, transferRequest.amount)
        amlValidationService.checkWalletsAreNotTheSame(originWallet, destinationWallet)
    }
}
