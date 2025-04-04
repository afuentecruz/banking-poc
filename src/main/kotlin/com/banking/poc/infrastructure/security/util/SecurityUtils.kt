package com.banking.poc.infrastructure.security.util

import com.banking.poc.infrastructure.security.dto.UserSecurity
import org.springframework.security.core.context.SecurityContextHolder
import java.security.Principal


class SecurityUtils {
    companion object {
        fun getUsername(): String {
            val securityContext = SecurityContextHolder.getContext()
            return (securityContext.authentication.principal as UserSecurity).username
        }
    }
}