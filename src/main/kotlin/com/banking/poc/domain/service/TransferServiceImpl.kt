package com.banking.poc.domain.service

import com.banking.poc.application.service.TransferService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.repository.TransferRepository

class TransferServiceImpl(
    private val transferRepository: TransferRepository
) : TransferService {

    override fun doTransfer(origin: Wallet, destination: Wallet, money: Money): Transfer {
        try {
            Transfer(
                money = money, origin = origin, destination = destination, status = TransferStatus.PENDING
            ).let {
                transferRepository.save(it).apply {
                    return transferRepository.save(this.copy(status = TransferStatus.COMPLETED))
                }
            }
        } catch (ex: Exception) {
            return transferRepository.save(
                Transfer(
                    money = money, origin = origin, destination = destination, status = TransferStatus.ERROR
                )
            )
        }
    }
}
