package com.banking.poc.infrastructure.repository.h2.transfer

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataH2TransferRepository : JpaRepository<TransferEntity, Long>
