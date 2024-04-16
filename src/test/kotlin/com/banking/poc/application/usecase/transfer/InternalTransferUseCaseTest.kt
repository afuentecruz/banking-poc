package com.banking.poc.application.usecase.transfer

import com.banking.poc.application.service.AmlValidationService
import com.banking.poc.application.service.TransferService
import com.banking.poc.application.service.WalletService
import com.banking.poc.application.usecase.movement.WithdrawMovementUseCase
import com.banking.poc.domain.exception.ForbiddenWalletUsageException
import com.banking.poc.domain.exception.InsufficientFundsException
import com.banking.poc.domain.exception.SameWalletsException
import com.banking.poc.domain.exception.WalletCurrencyNotFoundException
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.movement.Movement
import com.banking.poc.domain.model.movement.MovementType
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.infrastructure.rest.transfer.dto.TransferRequest
import com.banking.poc.utils.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class InternalTransferUseCaseTest {

    private val withdrawMovementUseCase: WithdrawMovementUseCase = mockk()
    private val depositMovementUseCase: com.banking.poc.application.usecase.movement.DepositMovementUseCase = mockk()
    private var walletService: WalletService = mockk()
    private var transferService: TransferService = mockk()
    private var amlValidationService: AmlValidationService = mockk()

    private val internalTransferUseCase: InternalTransferUseCase = InternalTransferUseCase(
        withdrawMovementUseCase, depositMovementUseCase, walletService, transferService, amlValidationService
    )

    @Test
    fun internalTransferOverNonExistingOriginWalletCurrency_shouldThrowWalletCurrencyNotFoundException() {
        // given
        val originWalletId = 1L
        val currency: String = MoneyCurrency.EUR.name
        val user: User = User.testData()
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        every { walletService.findWalletCurrency(originWalletId, currency) } throws WalletCurrencyNotFoundException(
            originWalletId, currency
        )

        // when
        val result = Assertions.assertThrows(WalletCurrencyNotFoundException::class.java) {
            internalTransferUseCase.internalTransfer(user.username, transferRequest)
        }

        // then
        Assertions.assertEquals(("Wallet with id $originWalletId and currency $currency not found"), result.message)
        verify { walletService.findWalletCurrency(originWalletId, currency) }
    }


    @Test
    fun internalTransferOverNonExistingDestinationWalletCurrency_shouldThrowWalletCurrencyNotFoundException() {
        // given
        val originWallet = Wallet.testData()
        val destinationWalletId = 2L

        val currency: String = MoneyCurrency.EUR.name
        val user: User = User.testData()
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        every { walletService.findWalletCurrency(originWallet.id!!, currency) } returns originWallet
        every {
            walletService.findWalletCurrency(
                destinationWalletId, currency
            )
        } throws WalletCurrencyNotFoundException(destinationWalletId, currency)

        // when
        val result = Assertions.assertThrows(WalletCurrencyNotFoundException::class.java) {
            internalTransferUseCase.internalTransfer(user.username, transferRequest)
        }

        // then
        Assertions.assertEquals(
            ("Wallet with id $destinationWalletId and currency $currency not found"), result.message
        )
        verify { walletService.findWalletCurrency(originWallet.id!!, currency) }
        verify { walletService.findWalletCurrency(destinationWalletId, currency) }
    }


    @Test
    fun internalTransferBetweenExistingWallets_butUserHasNoOwnership_shouldThrowForbiddenWalletUsageException() {
        // given
        val user = User.testData()
        val origin = Wallet.testData(id = 1L)
        val destination = Wallet.testData(id = 2L)

        val currency: String = MoneyCurrency.EUR.name
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        every { walletService.findWalletCurrency(origin.id!!, currency) } returns origin
        every {
            walletService.findWalletCurrency(
                destination.id!!, currency
            )
        } returns destination
        every {
            amlValidationService.checkWalletOwnership(user.username, origin.id!!)
        } throws ForbiddenWalletUsageException()

        // when
        val result = Assertions.assertThrows(ForbiddenWalletUsageException::class.java) {
            internalTransferUseCase.internalTransfer(user.username, transferRequest)
        }

        // then
        Assertions.assertEquals(
            ("You are not allowed to use this wallet"), result.message
        )
        verify { walletService.findWalletCurrency(origin.id!!, currency) }
        verify { walletService.findWalletCurrency(destination.id!!, currency) }
        verify { amlValidationService.checkWalletOwnership(user.username, origin.id!!) }
    }

    @Test
    fun internalTransferBetweenExistingWallets_butOriginWalletDoesNotHaveEnoughFunds_shouldThrowInsufficientFundsException() {
        // given
        val user = User.testData()
        val origin = Wallet.testData(id = 1L, balance = Money(BigDecimal.ONE, currency = MoneyCurrency.EUR))
        val destination = Wallet.testData(id = 2L)

        val currency: String = MoneyCurrency.EUR.name
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        every { walletService.findWalletCurrency(origin.id!!, currency) } returns origin
        every {
            walletService.findWalletCurrency(
                destination.id!!, currency
            )
        } returns destination
        every {
            amlValidationService.checkWalletOwnership(user.username, origin.id!!)
        } returns Unit
        every {
            amlValidationService.checkWalletBalance(origin, transferRequest.amount)
        } throws InsufficientFundsException()

        // when
        val result = Assertions.assertThrows(InsufficientFundsException::class.java) {
            internalTransferUseCase.internalTransfer(user.username, transferRequest)
        }

        // then
        Assertions.assertEquals(
            ("Wallet hasn't had enough funds to perform this operation"), result.message
        )
        verify { walletService.findWalletCurrency(origin.id!!, currency) }
        verify { walletService.findWalletCurrency(destination.id!!, currency) }
        verify { amlValidationService.checkWalletOwnership(user.username, origin.id!!) }
        verify { amlValidationService.checkWalletBalance(origin, transferRequest.amount) }
    }

    @Test
    fun internalTransferBetweenExistingWallets_butWalletsAreTheSame_shouldSameWalletsException() {
        // given
        val user = User.testData()
        val origin = Wallet.testData(id = 1L, balance = Money(BigDecimal.ONE, currency = MoneyCurrency.EUR))
        val destination = Wallet.testData(id = 2L)

        val currency: String = MoneyCurrency.EUR.name
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        every { walletService.findWalletCurrency(origin.id!!, currency) } returns origin
        every {
            walletService.findWalletCurrency(
                destination.id!!, currency
            )
        } returns destination
        every {
            amlValidationService.checkWalletOwnership(user.username, origin.id!!)
        } returns Unit
        every {
            amlValidationService.checkWalletBalance(origin, transferRequest.amount)
        } returns Unit
        every {
            amlValidationService.checkWalletsAreNotTheSame(origin, destination)
        } throws SameWalletsException()

        // when
        val result = Assertions.assertThrows(SameWalletsException::class.java) {
            internalTransferUseCase.internalTransfer(user.username, transferRequest)
        }

        // then
        Assertions.assertEquals(
            ("Origin and destination Wallet cannot be the same"), result.message
        )
        verify { walletService.findWalletCurrency(origin.id!!, currency) }
        verify { walletService.findWalletCurrency(destination.id!!, currency) }
        verify { amlValidationService.checkWalletOwnership(user.username, origin.id!!) }
        verify { amlValidationService.checkWalletBalance(origin, transferRequest.amount) }
        verify { amlValidationService.checkWalletsAreNotTheSame(origin, destination) }
    }

    @Test
    fun internalTransferGenerateACompletedTransfer_shouldCallWithdrawAndDepositUseCase() {
        // given
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        val user = User.testData()
        val origin = Wallet.testData(id = 1L, balance = Money(BigDecimal.TEN, currency = MoneyCurrency.EUR))
        val destination = Wallet.testData(id = 2L)
        val currency: String = MoneyCurrency.EUR.name
        val transferMoney =
            Money(amount = transferRequest.amount, currency = MoneyCurrency.valueOf(transferRequest.currency))
        val completedTransfer = Transfer.testData(
            origin = origin,
            destination = destination,
            status = TransferStatus.COMPLETED
        )

        every { walletService.findWalletCurrency(origin.id!!, currency) } returns origin
        every {
            walletService.findWalletCurrency(
                destination.id!!, currency
            )
        } returns destination
        every {
            amlValidationService.checkWalletOwnership(user.username, origin.id!!)
        } returns Unit
        every {
            amlValidationService.checkWalletBalance(origin, transferRequest.amount)
        } returns Unit
        every {
            amlValidationService.checkWalletsAreNotTheSame(origin, destination)
        } returns Unit
        every {
            transferService.doTransfer(origin, destination, transferMoney)
        } returns completedTransfer
        every {
            withdrawMovementUseCase.withdrawMovement(origin, transferMoney)
        } returns Movement.testData(type = MovementType.WITHDRAW)
        every {
            depositMovementUseCase.depositMovement(destination, transferMoney)
        } returns Movement.testData(type = MovementType.DEPOSIT)
        // when
        internalTransferUseCase.internalTransfer(user.username, transferRequest)

        // then
        verify { walletService.findWalletCurrency(origin.id!!, currency) }
        verify { walletService.findWalletCurrency(destination.id!!, currency) }
        verify { amlValidationService.checkWalletOwnership(user.username, origin.id!!) }
        verify { amlValidationService.checkWalletBalance(origin, transferRequest.amount) }
        verify { amlValidationService.checkWalletsAreNotTheSame(origin, destination) }
        verify {
            transferService.doTransfer(origin, destination, transferMoney)
        }
        verify { withdrawMovementUseCase.withdrawMovement(origin, transferMoney) }
        verify { depositMovementUseCase.depositMovement(destination, transferMoney) }
    }

    @Test
    fun internalTransferGenerateAnErrorTransfer_shouldCallWithdrawAndDepositUseCase() {
        // given
        val transferRequest = TransferRequest(
            amount = BigDecimal.TEN, currency = MoneyCurrency.EUR.name, from = 1L, to = 2L
        )
        val user = User.testData()
        val origin = Wallet.testData(id = 1L, balance = Money(BigDecimal.TEN, currency = MoneyCurrency.EUR))
        val destination = Wallet.testData(id = 2L)
        val currency: String = MoneyCurrency.EUR.name
        val transferMoney =
            Money(amount = transferRequest.amount, currency = MoneyCurrency.valueOf(transferRequest.currency))
        val errorTransfer = Transfer.testData(
            origin = origin,
            destination = destination,
            status = TransferStatus.ERROR
        )

        every { walletService.findWalletCurrency(origin.id!!, currency) } returns origin
        every {
            walletService.findWalletCurrency(
                destination.id!!, currency
            )
        } returns destination
        every {
            amlValidationService.checkWalletOwnership(user.username, origin.id!!)
        } returns Unit
        every {
            amlValidationService.checkWalletBalance(origin, transferRequest.amount)
        } returns Unit
        every {
            amlValidationService.checkWalletsAreNotTheSame(origin, destination)
        } returns Unit
        every {
            transferService.doTransfer(origin, destination, transferMoney)
        } returns errorTransfer

        // when
        internalTransferUseCase.internalTransfer(user.username, transferRequest)

        // then
        verify { walletService.findWalletCurrency(origin.id!!, currency) }
        verify { walletService.findWalletCurrency(destination.id!!, currency) }
        verify { amlValidationService.checkWalletOwnership(user.username, origin.id!!) }
        verify { amlValidationService.checkWalletBalance(origin, transferRequest.amount) }
        verify { amlValidationService.checkWalletsAreNotTheSame(origin, destination) }
        verify {
            transferService.doTransfer(origin, destination, transferMoney)
        }
        verify(exactly = 0) { withdrawMovementUseCase.withdrawMovement(origin, transferMoney) }
        verify(exactly = 0) { depositMovementUseCase.depositMovement(destination, transferMoney) }
    }


}
