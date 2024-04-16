package com.banking.poc.domain.exception

class UsernameNotFoundException(username: String) : RuntimeException("User with username $username not found")
