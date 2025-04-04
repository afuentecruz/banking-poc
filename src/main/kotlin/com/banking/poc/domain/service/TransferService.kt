package com.banking.poc.domain.service

import com.banking.poc.application.port.outbound.repository.TransferRepositoryPort
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.wallet.Wallet

class TransferService(
    private val transferRepositoryPort: TransferRepositoryPort
) {

    fun doTransfer(origin: Wallet, destination: Wallet, money: Money): Transfer {
        try {
            Transfer(
                money = money, origin = origin, destination = destination, status = TransferStatus.PENDING
            ).let {
                transferRepositoryPort.save(it).apply {
                    return transferRepositoryPort.save(this.copy(status = TransferStatus.COMPLETED))
                }
            }
        } catch (ex: Exception) {
            return transferRepositoryPort.save(
                Transfer(
                    money = money, origin = origin, destination = destination, status = TransferStatus.ERROR
                )
            )
        }
    }
}
