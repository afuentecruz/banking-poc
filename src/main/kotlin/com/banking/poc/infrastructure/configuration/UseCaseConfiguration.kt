package com.banking.poc.infrastructure.configuration

import com.banking.poc.application.usecase.movement.WalletMovementUseCase
import com.banking.poc.application.usecase.movement.WithdrawMovementUseCase
import com.banking.poc.application.usecase.transfer.InternalTransferUseCase
import com.banking.poc.application.usecase.user.UserDetailsUseCase
import com.banking.poc.application.usecase.user.UserRegistryUseCase
import com.banking.poc.application.usecase.wallet.WalletCreateUseCase
import com.banking.poc.application.usecase.wallet.WalletDepositUseCase
import com.banking.poc.application.usecase.wallet.WalletDetailsUseCase
import com.banking.poc.domain.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class UseCaseConfiguration {

    @Bean
    fun createUserUseCase(
        userService: UserService, passwordEncoder: BCryptPasswordEncoder
    ): UserRegistryUseCase = UserRegistryUseCase(userService, passwordEncoder)

    @Bean
    fun findUserUseCase(
        userService: UserService
    ): UserDetailsUseCase = UserDetailsUseCase(userService)

    @Bean
    fun createWalletUseCase(
        userService: UserService, walletService: WalletService
    ): WalletCreateUseCase = WalletCreateUseCase(userService, walletService)

    @Bean
    fun walletDetailsUseCase(
        walletService: WalletService, userService: UserService, amlValidationService: AmlValidationService
    ): WalletDetailsUseCase = WalletDetailsUseCase(walletService, userService, amlValidationService)

    @Bean
    fun walletDepositUseCase(
        walletService: WalletService, movementService: MovementService
    ): WalletDepositUseCase = WalletDepositUseCase(walletService, movementService)

    @Bean
    fun internalTransferUSeCase(
        withdrawMovementUseCase: WithdrawMovementUseCase,
        depositMovementUseCase: com.banking.poc.application.usecase.movement.DepositMovementUseCase,
        walletService: WalletService,
        movementService: MovementService,
        transferService: TransferService,
        amlValidationService: AmlValidationService
    ): InternalTransferUseCase = InternalTransferUseCase(
        withdrawMovementUseCase, depositMovementUseCase, walletService, transferService, amlValidationService
    )

    @Bean
    fun walletMovementUseCase(
        amlValidationService: AmlValidationService, movementService: MovementService
    ): WalletMovementUseCase = WalletMovementUseCase(amlValidationService, movementService)

    @Bean
    fun withdrawMovementUseCase(
        walletService: WalletService, movementService: MovementService
    ): WithdrawMovementUseCase = WithdrawMovementUseCase(walletService, movementService)

    @Bean
    fun depositMovementUseCase(
        walletService: WalletService, movementService: MovementService
    ): com.banking.poc.application.usecase.movement.DepositMovementUseCase =
        com.banking.poc.application.usecase.movement.DepositMovementUseCase(walletService, movementService)
}
