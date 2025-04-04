package com.banking.poc.infrastructure.adapter.outbound.repository.h2.transfer

import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.application.port.outbound.repository.TransferRepositoryPort
import org.springframework.stereotype.Component

@Component
class H2TransferRepositoryAdapter(private val h2TransferRepository: SpringDataH2TransferRepository) : TransferRepositoryPort {
    override fun save(transfer: Transfer): Transfer =
        h2TransferRepository.save(transfer.toEntity()).toDomain()
}
