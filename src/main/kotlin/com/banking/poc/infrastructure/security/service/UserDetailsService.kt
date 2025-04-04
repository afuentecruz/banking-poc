package com.banking.poc.infrastructure.security.service

import com.banking.poc.domain.service.UserService
import com.banking.poc.infrastructure.security.dto.UserSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserDetailsService(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findUsername(username)
        return UserSecurity(
            user.id.toString(),
            user.username,
            user.password,
            Collections.singleton(SimpleGrantedAuthority("USER"))
        )
    }
}
