package com.banking.poc.application.usecase.user

import com.banking.poc.application.service.UserService
import com.banking.poc.infrastructure.rest.user.dto.UserResponse
import com.banking.poc.infrastructure.rest.user.dto.fromDomain

class UserDetailsUseCase(private val userService: UserService) {
    fun findAllUsers(): List<UserResponse> = userService.findAll().map { UserResponse.fromDomain(it) }
}
