package com.banking.poc.application.port.inbound.usecase

import com.banking.poc.domain.dto.user.RegisterUserRequest
import com.banking.poc.domain.dto.user.UserResponse

interface UserUseCasePort {
    fun createUser(request: RegisterUserRequest): UserResponse
    fun findAllUsers(): List<UserResponse>
}