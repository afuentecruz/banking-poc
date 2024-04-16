package com.banking.poc.infrastructure.rest.transfer.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class TransferRequest(
    @field:NotNull(message = "amount is mandatory")
    @field:Min(1)
    val amount: BigDecimal,
    @field:NotBlank(message = "currency cannot be empty")
    val currency: String,
    @field:NotNull(message = "origin account is mandatory")
    val from: Long,
    @field:NotNull(message = "destination account is mandatory")
    val to: Long
)
