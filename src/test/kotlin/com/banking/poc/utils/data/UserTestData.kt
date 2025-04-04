package com.banking.poc.utils.data

import com.banking.poc.domain.model.user.User

fun User.Companion.testData(
    userId: Long? = 1,
    username: String = "usernameTest",
    password: String = "passwordTest"
) = User(id = userId, username = username, password = password)
