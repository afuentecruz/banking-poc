package com.banking.poc.domain.model.money

import java.math.BigDecimal

data class Money(var amount: BigDecimal, val currency: MoneyCurrency) {
    companion object
}
