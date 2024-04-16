package com.banking.poc.domain.service

import com.banking.poc.application.service.UserService
import com.banking.poc.domain.exception.UsernameNotFoundException
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.repository.UserRepository

class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun findAll(): List<User> = userRepository.findAllUsers()

    override fun findUser(userId: Long): User =
        userRepository.findUserById(userId)

    override fun findUsername(username: String): User =
        userRepository.findUsername(username) ?: throw UsernameNotFoundException(username)

    override fun createUser(user: User): User = userRepository.createUser(user)

}
