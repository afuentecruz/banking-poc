package com.banking.poc.infrastructure.rest.user.dto

import java.beans.ConstructorProperties

data class LoginRequest
@ConstructorProperties("username", "password")
constructor(val username: String, val password: String)
