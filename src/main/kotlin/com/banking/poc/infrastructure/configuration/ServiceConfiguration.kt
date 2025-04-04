package com.banking.poc.infrastructure.configuration

import com.banking.poc.application.port.outbound.repository.MovementRepositoryPort
import com.banking.poc.application.port.outbound.repository.TransferRepositoryPort
import com.banking.poc.application.port.outbound.repository.UserRepositoryPort
import com.banking.poc.application.port.outbound.repository.WalletRepositoryPort
import com.banking.poc.domain.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun userServiceImpl(
        userRepository: UserRepositoryPort,
    ): UserService = UserService(userRepository)

    @Bean
    fun walletServiceImpl(
        walletRepository: WalletRepositoryPort
    ): WalletService = WalletService(walletRepository)

    @Bean
    fun movementServiceImpl(
        movementRepository: MovementRepositoryPort
    ): MovementService = MovementService(movementRepository)

    @Bean
    fun transferServiceImpl(
        transferRepositoryPort: TransferRepositoryPort
    ): TransferService = TransferService(transferRepositoryPort)

    @Bean
    fun amlValidationServiceImpl(
        walletService: WalletService,
        userService: UserService
    ): AmlValidationService = AmlValidationService(walletService, userService)
}
