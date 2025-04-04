package com.banking.poc.application.port.inbound.rest

import com.banking.poc.domain.dto.user.RegisterUserRequest
import com.banking.poc.domain.dto.user.UserResponse

interface UserRestInbound {
    fun createUser(request: RegisterUserRequest): UserResponse
    fun findAllUsers(): List<UserResponse>
}