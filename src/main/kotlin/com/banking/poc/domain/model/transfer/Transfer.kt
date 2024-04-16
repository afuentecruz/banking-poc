package com.banking.poc.domain.model.transfer

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.wallet.Wallet

data class Transfer(
    val transferId: Long? = null,
    val money: Money,
    val origin: Wallet,
    val destination: Wallet,
    val status: TransferStatus
) {
    companion object
}
