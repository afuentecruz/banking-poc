package com.banking.poc.infrastructure.adapter.inbound.usecase.controller

import com.banking.poc.application.port.inbound.usecase.UserUseCasePort
import com.banking.poc.application.usecase.user.UserDetailsUseCase
import com.banking.poc.application.usecase.user.UserRegistryUseCase
import com.banking.poc.domain.dto.user.RegisterUserRequest
import com.banking.poc.domain.dto.user.UserResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/users"])
class UserControllerAdapter(
    val registerUserUseCase: UserRegistryUseCase, val findUserUseCase: UserDetailsUseCase
) : UserUseCasePort {

    @PostMapping(path = ["/registry"])
    override fun createUser(@Valid @RequestBody request: RegisterUserRequest): UserResponse =
        registerUserUseCase.registerUser(request)

    @GetMapping
    override fun findAllUsers(): List<UserResponse> =
        findUserUseCase.findAllUsers()

}

