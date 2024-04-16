package com.banking.poc.infrastructure.repository.h2.wallet

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataH2WalletRepository : JpaRepository<WalletEntity, Long> {
    fun findAllByUserId(userId: Long): List<WalletEntity>
    fun findByIdAndCurrency(walletId: Long, currency: String): WalletEntity?
}

