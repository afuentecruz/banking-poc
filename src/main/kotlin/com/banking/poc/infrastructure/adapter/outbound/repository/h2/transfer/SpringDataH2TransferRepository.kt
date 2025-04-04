package com.banking.poc.infrastructure.adapter.outbound.repository.h2.transfer

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataH2TransferRepository : JpaRepository<TransferEntity, Long>
