package com.banking.poc.infrastructure.configuration

import com.banking.poc.application.port.outbound.repository.MovementRepositoryOutbound
import com.banking.poc.application.port.outbound.repository.TransferRepositoryOutbound
import com.banking.poc.application.port.outbound.repository.UserRepositoryOutbound
import com.banking.poc.application.port.outbound.repository.WalletRepositoryOutbound
import com.banking.poc.domain.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun userServiceImpl(
        userRepository: UserRepositoryOutbound,
    ): UserService = UserService(userRepository)

    @Bean
    fun walletServiceImpl(
        walletRepository: WalletRepositoryOutbound
    ): WalletService = WalletService(walletRepository)

    @Bean
    fun movementServiceImpl(
        movementRepository: MovementRepositoryOutbound
    ): MovementService = MovementService(movementRepository)

    @Bean
    fun transferServiceImpl(
        transferRepositoryPort: TransferRepositoryOutbound
    ): TransferService = TransferService(transferRepositoryPort)

    @Bean
    fun amlValidationServiceImpl(
        walletService: WalletService,
        userService: UserService
    ): AmlValidationService = AmlValidationService(walletService, userService)
}
