package com.banking.poc.infrastructure.adapter.inbound.usecase.controller

import com.banking.poc.application.port.inbound.usecase.TransferUseCasePort
import com.banking.poc.application.usecase.transfer.InternalTransferUseCase
import com.banking.poc.domain.dto.transfer.TransferRequest
import com.banking.poc.infrastructure.security.util.SecurityUtils
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/transfers")
class TransferControllerAdapter(
    private val internalTransferUseCase: InternalTransferUseCase
) : TransferUseCasePort {

    @PostMapping(value = ["/internal"])
    override fun internalTransfer(
        @Valid @RequestBody transferRequest: TransferRequest
    ) = internalTransferUseCase.internalTransfer(SecurityUtils.getUsername(), transferRequest)
}
