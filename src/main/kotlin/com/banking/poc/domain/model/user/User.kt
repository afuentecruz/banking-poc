package com.banking.poc.domain.model.user

data class User(
    val id: Long? = null,
    val username: String,
    val password: String
) {
    companion object
}
