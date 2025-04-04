package com.banking.poc.domain.service

import com.banking.poc.domain.exception.UsernameNotFoundException
import com.banking.poc.domain.model.user.User
import com.banking.poc.application.port.outbound.repository.UserRepositoryPort

class UserService(
    private val userRepository: UserRepositoryPort,
)  {

    fun findAll(): List<User> = userRepository.findAllUsers()

    fun findUser(userId: Long): User =
        userRepository.findUserById(userId)

    fun findUsername(username: String): User =
        userRepository.findUsername(username) ?: throw UsernameNotFoundException(username)

    fun createUser(user: User): User = userRepository.createUser(user)

}
