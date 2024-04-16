package com.banking.poc.domain.exception

class UserNotFoundException(userId: Long) : RuntimeException("User with id $userId not found")
