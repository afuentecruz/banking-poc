package com.banking.poc.application.usecase.user

import com.banking.poc.application.service.UserService
import com.banking.poc.domain.model.user.User
import com.banking.poc.utils.testData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserDetailsUseCaseTest {

    private val userService: UserService = mockk()
    private val userDetailUseCase: UserDetailsUseCase = UserDetailsUseCase(userService)

    @Test
    fun whenFindAllUsers_shouldCallUserService_thenReturnAllUserResponseList() {
        // given
        val user: User = User.testData()
        val userList: List<User> = listOf(user)
        every { userService.findAll() } returns userList
        // when
        val result = userDetailUseCase.findAllUsers()
        // then
        Assertions.assertEquals(user.id, result.first().id)
        Assertions.assertEquals(user.username, result.first().username)
        verify(exactly = 1) { userService.findAll() }
    }

    @Test
    fun whenFindAllUsersWithNoUsers_shouldCallUserService_thenReturnEmptyList() {
        // given
        val userList: List<User> = emptyList()
        every { userService.findAll() } returns userList
        // when
        val result = userDetailUseCase.findAllUsers()
        // then
        Assertions.assertTrue(result.isEmpty())
        verify(exactly = 1) { userService.findAll() }
    }

}
