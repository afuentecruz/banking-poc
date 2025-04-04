package com.banking.poc.application.port.outbound.repository

import com.banking.poc.domain.model.transfer.Transfer


interface TransferRepositoryOutbound {
    fun save(transfer: Transfer): Transfer
}
