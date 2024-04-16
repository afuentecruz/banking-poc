package com.banking.poc.domain.repository

import com.banking.poc.domain.model.transfer.Transfer


interface TransferRepository {
    fun save(transfer: Transfer): Transfer
}
