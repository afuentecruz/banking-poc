package com.banking.poc.domain.exception

class UserWalletNotFoundException(userId: Long) : RuntimeException("Wallet for userId $userId not found")
