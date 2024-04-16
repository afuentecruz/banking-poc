package com.banking.poc.infrastructure.rest.user.dto

import com.banking.poc.domain.model.user.User

data class UserResponse(
    val id: Long, val username: String
) {
    companion object
}

fun UserResponse.Companion.fromDomain(user: User) =
    UserResponse(id = user.id!!, username = user.username)
