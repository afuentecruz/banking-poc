package com.banking.poc.infrastructure.repository.h2.transfer

import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.repository.TransferRepository
import org.springframework.stereotype.Component

@Component
class H2TransferRepository(private val h2TransferRepository: SpringDataH2TransferRepository) : TransferRepository {
    override fun save(transfer: Transfer): Transfer =
        h2TransferRepository.save(transfer.toEntity()).toDomain()
}
