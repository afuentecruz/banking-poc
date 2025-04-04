package com.banking.poc.infrastructure.adapter.outbound.repository.h2.wallet

import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.application.port.outbound.repository.WalletRepositoryPort
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class H2WalletRepositoryAdapter(private val h2WalletRepository: SpringDataH2WalletRepository) : WalletRepositoryPort {

    override fun saveWallet(wallet: Wallet): Wallet =
        h2WalletRepository.save(wallet.toEntity()).toDomain()

    override fun findWallet(walletId: Long): Wallet? =
        h2WalletRepository.findById(walletId).getOrNull()?.toDomain()

    override fun findWalletsByUserId(userId: Long): List<Wallet> =
        h2WalletRepository.findAllByUserId(userId).map { it.toDomain() }

    override fun findWalletCurrency(walletId: Long, currency: String): Wallet? =
        h2WalletRepository.findByIdAndCurrency(walletId, currency)?.toDomain()

}
