package com.banking.poc.utils.data

import com.banking.poc.domain.model.money.Money
import com.banking.poc.domain.model.money.MoneyCurrency
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.model.wallet.Wallet
import java.math.BigDecimal

fun Wallet.Companion.testData(
    id: Long? = 1L,
    user: User = User.testData(),
    balance: Money = Money(amount = BigDecimal.ZERO, currency = MoneyCurrency.EUR)
) = Wallet(id = id, user = user, balance = balance)

