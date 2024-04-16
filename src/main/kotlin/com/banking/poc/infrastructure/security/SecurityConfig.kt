package com.banking.poc.infrastructure.security

import com.banking.poc.infrastructure.security.filter.JwtAuthenticationFilter
import com.banking.poc.infrastructure.security.filter.JwtAuthorizationFilter
import com.banking.poc.infrastructure.security.service.TokenService
import com.banking.poc.infrastructure.security.service.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {
    private val jwtToken = TokenService()

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)
        // Put your endpoint to create/sign, otherwise spring will secure it as
        // well you won't be able to do any request
        http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/users/registry", "/login").permitAll()
            it.requestMatchers("/h2-console/**").permitAll()
            it.requestMatchers("/swagger-ui/**").permitAll()
            it.requestMatchers("/v3/api-docs/**").permitAll()
            it.anyRequest().authenticated()
        }.csrf { it.disable() }
            .authenticationManager(authenticationManager)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
            .addFilter(JwtAuthorizationFilter(jwtToken, userDetailsService, authenticationManager))
        http.headers {
            it.frameOptions { it.disable() }
        }
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
