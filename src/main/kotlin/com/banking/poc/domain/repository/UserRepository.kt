package com.banking.poc.domain.repository

import com.banking.poc.domain.model.user.User


interface UserRepository {
    fun findAllUsers(): List<User>
    fun findUsername(username: String): User?
    fun createUser(user: User): User
    fun findUserById(id: Long): User
}
