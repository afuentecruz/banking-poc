package com.banking.poc.application.port.outbound.repository

import com.banking.poc.domain.model.user.User


interface UserRepositoryPort {
    fun findAllUsers(): List<User>
    fun findUsername(username: String): User?
    fun createUser(user: User): User
    fun findUserById(id: Long): User
}
