package com.banking.poc.infrastructure.adapter.repository.h2.transfer

import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.application.port.outbound.repository.TransferRepositoryOutbound
import org.springframework.stereotype.Component

@Component
class H2TransferRepositoryAdapter(private val h2TransferRepository: SpringDataH2TransferRepository) : TransferRepositoryOutbound {
    override fun save(transfer: Transfer): Transfer =
        h2TransferRepository.save(transfer.toEntity()).toDomain()
}
