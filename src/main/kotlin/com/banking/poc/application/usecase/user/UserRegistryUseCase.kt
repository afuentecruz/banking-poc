package com.banking.poc.application.usecase.user

import com.banking.poc.domain.dto.user.RegisterUserRequest
import com.banking.poc.domain.dto.user.UserResponse
import com.banking.poc.domain.dto.user.fromDomain
import com.banking.poc.domain.dto.user.toDomain
import com.banking.poc.domain.service.UserService
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
