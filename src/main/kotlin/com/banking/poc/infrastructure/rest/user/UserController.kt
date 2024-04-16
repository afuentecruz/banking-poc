package com.banking.poc.infrastructure.rest.user

import com.banking.poc.application.usecase.user.UserDetailsUseCase
import com.banking.poc.application.usecase.user.UserRegistryUseCase
import com.banking.poc.infrastructure.rest.user.dto.RegisterUserRequest
import com.banking.poc.infrastructure.rest.user.dto.UserResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping(path = ["/users"])
class UserController(val registerUserUseCase: UserRegistryUseCase, val findUserUseCase: UserDetailsUseCase) {

    @GetMapping
    fun findAllUsers(principal: Principal): ResponseEntity<List<UserResponse>> = run {
        findUserUseCase.findAllUsers().let { ResponseEntity.ok(it) }
    }

    @PostMapping(path = ["/registry"])
    fun createUser(@Valid @RequestBody registerUserRequest: RegisterUserRequest): ResponseEntity<UserResponse> =
        registerUserUseCase.registerUser(registerUserRequest).let { ResponseEntity.ok(it) }
}
