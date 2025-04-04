package com.banking.poc.application.port.inbound.rest

import com.banking.poc.domain.dto.transfer.TransferRequest

interface TransferRestInbound {
    fun internalTransfer(transferRequest: TransferRequest)
}