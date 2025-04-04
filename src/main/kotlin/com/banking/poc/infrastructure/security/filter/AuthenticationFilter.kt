package com.banking.poc.infrastructure.security.filter

import com.banking.poc.domain.dto.user.UserLoginRequest
import com.banking.poc.infrastructure.security.dto.UserSecurity
import com.banking.poc.infrastructure.security.service.TokenService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

class JwtAuthenticationFilter(
    private val tokenService: TokenService, private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(req.inputStream, UserLoginRequest::class.java)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.username, credentials.password, Collections.singleton(SimpleGrantedAuthority("user"))
        )
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?, auth: Authentication
    ) {
        val username = (auth.principal as UserSecurity).username
        val token: String = tokenService.generateToken(username)
        res.addHeader("Authorization", token)
        res.addHeader("Access-Control-Expose-Headers", "Authorization")
        res.addHeader(HttpHeaders.CONTENT_TYPE, "Application/json")
        res.writer.write("{\"token\": \"$token\"}")
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException
    ) {
        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

}

private data class BadCredentialsError(
    val timestamp: Long = Date().time,
    val status: Int = 401,
    val message: String = "Email or password incorrect",
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
