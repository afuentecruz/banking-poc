package com.banking.poc.application.service

import com.banking.poc.domain.model.user.User


interface UserService {
    fun findAll(): List<User>
    fun findUser(userId: Long): User
    fun findUsername(username: String): User
    fun createUser(user: User): User
}

