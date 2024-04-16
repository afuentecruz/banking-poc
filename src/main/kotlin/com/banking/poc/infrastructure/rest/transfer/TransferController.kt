package com.banking.poc.infrastructure.rest.transfer

import com.banking.poc.application.usecase.transfer.InternalTransferUseCase
import com.banking.poc.infrastructure.rest.transfer.dto.TransferRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal

@Controller
@RequestMapping("/transfers")
class TransferController(private val internalTransferUseCase: InternalTransferUseCase) {

    @PostMapping(value = ["/internal"])
    fun internalTransfer(
        principal: Principal,
        @Valid @RequestBody transferRequest: TransferRequest
    ): ResponseEntity<Unit> =
        internalTransferUseCase.internalTransfer(principal.name, transferRequest)
            .let { ResponseEntity.ok().build() }
}
