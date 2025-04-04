package com.banking.poc.application.port.inbound.usecase

import com.banking.poc.domain.dto.transfer.TransferRequest

interface TransferUseCasePort {
    fun internalTransfer(transferRequest: TransferRequest)
}