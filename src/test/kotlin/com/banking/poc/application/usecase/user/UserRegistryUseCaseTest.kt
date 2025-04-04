package com.banking.poc.application.usecase.user

import com.banking.poc.domain.dto.user.RegisterUserRequest
import com.banking.poc.domain.dto.user.toDomain
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.service.UserService
import com.banking.poc.utils.data.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRegistryUseCaseTest {

    private var userService: UserService = mockk()

    private val passwordEncoder: BCryptPasswordEncoder = mockk()

    private val userRegistryUseCase: UserRegistryUseCase = UserRegistryUseCase(userService, passwordEncoder)


    @Test
    fun whenRegisterUser_shouldCallUserService_thenReturnCreatedUser() {
        // given
        val registerUserRequest: RegisterUserRequest =
            RegisterUserRequest(username = "usernameTest", password = "passwordTest")

        val securedUserRequest: RegisterUserRequest = registerUserRequest.copy(password = "encoded-test-password")
        val securedUser: User = securedUserRequest.toDomain()
        val createdUser: User = User.testData()
        every { passwordEncoder.encode(registerUserRequest.password) } returns "encoded-test-password"
        every { userService.createUser(securedUser) } returns createdUser
        // when
        val result = userRegistryUseCase.registerUser(registerUserRequest)
        // then
        Assertions.assertEquals(createdUser.id, 1)
        Assertions.assertEquals(createdUser.username, result.username)
        verify(exactly = 1) { passwordEncoder.encode("passwordTest") }
        verify(exactly = 1) { userService.createUser(securedUserRequest.toDomain()) }
    }
}
