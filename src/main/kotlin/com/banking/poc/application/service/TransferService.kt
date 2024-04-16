package com.banking.poc.application.service

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.wallet.Wallet

interface TransferService {
    fun doTransfer(origin: Wallet, destination: Wallet, money: Money): Transfer
}
