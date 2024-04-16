package com.banking.poc.application.usecase.user

import com.banking.poc.application.service.UserService
import com.banking.poc.infrastructure.rest.user.dto.RegisterUserRequest
import com.banking.poc.infrastructure.rest.user.dto.UserResponse
import com.banking.poc.infrastructure.rest.user.dto.fromDomain
import com.banking.poc.infrastructure.rest.user.dto.toDomain
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRegistryUseCase(
    private val userService: UserService, private val passwordEncoder: BCryptPasswordEncoder
) {

    fun registerUser(registerUserRequest: RegisterUserRequest): UserResponse =
        registerUserRequest.copy(password = passwordEncoder.encode(registerUserRequest.password)).let {
            it.toDomain().let { user ->
                userService.createUser(user).let { createdUser ->
                    UserResponse.fromDomain(createdUser)
                }
            }
        }

}
