package com.banking.poc.utils

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import java.math.BigDecimal

fun Money.Companion.testData(): Money =
    Money(amount = BigDecimal.TEN, currency = MoneyCurrency.EUR)
