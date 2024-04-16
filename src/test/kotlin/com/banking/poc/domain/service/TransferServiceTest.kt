package com.banking.poc.domain.service

import com.banking.poc.application.service.TransferService
import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.transfer.Transfer
import com.banking.poc.domain.model.transfer.TransferStatus
import com.banking.poc.domain.model.wallet.Wallet
import com.banking.poc.domain.repository.TransferRepository
import com.banking.poc.utils.testData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.dao.DataIntegrityViolationException

class TransferServiceTest {

    private var transferRepository: TransferRepository = mockk()

    private val transferService: TransferService = TransferServiceImpl(
        transferRepository
    )

    @Test
    fun whenDoTransfer_shouldSaveAndUpdateTransferStatus() {
        // given
        val origin = Wallet.testData()
        val destination = Wallet.testData()
        val money = Money.testData()
        val pendingTransfer = Transfer.testData(
            origin = origin,
            destination = destination,
            money = money,
            status = TransferStatus.PENDING
        )
        val completedTransfer = pendingTransfer.copy(status = TransferStatus.COMPLETED)
        every { transferRepository.save(any()) } returns pendingTransfer andThen completedTransfer
        // then
        val result = transferService.doTransfer(origin, destination, money)
        // then
        Assertions.assertEquals(completedTransfer.transferId, result.transferId)
        Assertions.assertEquals(completedTransfer.origin, result.origin)
        Assertions.assertEquals(completedTransfer.destination, result.destination)
        Assertions.assertEquals(completedTransfer.money, result.money)
        Assertions.assertEquals(completedTransfer.status, result.status)
    }

    @Test
    fun whenDoTransferButSomeExceptionIsThrown_shouldSaveTransferWithErrorStatus() {
        // given
        val origin = Wallet.testData()
        val destination = Wallet.testData()
        val money = Money.testData()
        val errorTransfer = Transfer.testData(
            origin = origin,
            destination = destination,
            money = money,
            status = TransferStatus.ERROR
        )
        every { transferRepository.save(any()) } throws DataIntegrityViolationException("test data integrity violation exception") andThen errorTransfer
        // then
        val result = transferService.doTransfer(origin, destination, money)
        // then
        Assertions.assertEquals(errorTransfer.transferId, result.transferId)
        Assertions.assertEquals(errorTransfer.origin, result.origin)
        Assertions.assertEquals(errorTransfer.destination, result.destination)
        Assertions.assertEquals(errorTransfer.money, result.money)
        Assertions.assertEquals(errorTransfer.status, result.status)
    }
}
