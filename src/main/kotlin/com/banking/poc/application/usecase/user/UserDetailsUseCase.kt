package com.banking.poc.application.usecase.user

import com.banking.poc.domain.dto.user.UserResponse
import com.banking.poc.domain.dto.user.fromDomain
import com.banking.poc.domain.service.UserService

class UserDetailsUseCase(private val userService: UserService) {
    fun findAllUsers(): List<UserResponse> = userService.findAll().map { UserResponse.fromDomain(it) }
}
