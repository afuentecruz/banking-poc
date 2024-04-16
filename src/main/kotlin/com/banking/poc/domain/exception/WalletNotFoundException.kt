package com.banking.poc.domain.exception

class WalletNotFoundException(walletId: Long) :
    RuntimeException("Wallet with id $walletId not found")
