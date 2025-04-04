package com.banking.poc.utils.data

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.wallet.Wallet

fun Transfer.Companion.testData(
    id: Long? = 1L,
    status: TransferStatus = TransferStatus.COMPLETED,
    origin: Wallet = Wallet.testData(),
    destination: Wallet = Wallet.testData(),
    money: Money = Money.testData()
): Transfer = Transfer(
    transferId = id, money = money, origin = origin, destination = destination, status = status
)
