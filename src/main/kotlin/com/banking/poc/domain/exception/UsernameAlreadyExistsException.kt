package com.banking.poc.domain.exception

class UsernameAlreadyExistsException(username: String) : RuntimeException("Username $username already exists")
