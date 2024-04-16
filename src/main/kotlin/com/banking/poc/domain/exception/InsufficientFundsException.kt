package com.banking.poc.domain.exception

class InsufficientFundsException : RuntimeException("Wallet hasn't had enough funds to perform this operation")
