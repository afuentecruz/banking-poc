package com.banking.poc.domain.dto.user

import java.beans.ConstructorProperties

data class UserLoginRequest
@ConstructorProperties("username", "password")
constructor(val username: String, val password: String)
