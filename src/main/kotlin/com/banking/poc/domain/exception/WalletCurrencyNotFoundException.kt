package com.banking.poc.domain.exception

class WalletCurrencyNotFoundException(walletId: Long, currency: String) :
    RuntimeException("Wallet with id $walletId and currency $currency not found")
