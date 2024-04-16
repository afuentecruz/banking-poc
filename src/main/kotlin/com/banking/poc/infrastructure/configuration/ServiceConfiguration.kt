package com.banking.poc.infrastructure.configuration

import com.banking.poc.application.service.UserService
import com.banking.poc.application.service.WalletService
import com.banking.poc.domain.repository.MovementRepository
import com.banking.poc.domain.repository.TransferRepository
import com.banking.poc.domain.repository.UserRepository
import com.banking.poc.domain.repository.WalletRepository
import com.banking.poc.domain.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun userServiceImpl(
        userRepository: UserRepository,
    ): UserServiceImpl = UserServiceImpl(userRepository)

    @Bean
    fun walletServiceImpl(
        walletRepository: WalletRepository
    ): WalletServiceImpl = WalletServiceImpl(walletRepository)

    @Bean
    fun movementServiceImpl(
        movementRepository: MovementRepository
    ): MovementServiceImpl = MovementServiceImpl(movementRepository)

    @Bean
    fun transferServiceImpl(
        transferRepository: TransferRepository
    ): TransferServiceImpl = TransferServiceImpl(transferRepository)

    @Bean
    fun amlValidationServiceImpl(
        walletService: WalletService,
        userService: UserService
    ): AmlValidationServiceImpl = AmlValidationServiceImpl(walletService, userService)
}
