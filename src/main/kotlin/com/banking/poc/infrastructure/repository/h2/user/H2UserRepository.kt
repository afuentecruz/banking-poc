package com.banking.poc.infrastructure.repository.h2.user

import com.banking.poc.domain.exception.UserNotFoundException
import com.banking.poc.domain.exception.UsernameAlreadyExistsException
import com.banking.poc.domain.model.user.User
import com.banking.poc.domain.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrElse

@Component
class H2UserRepository(private val h2UserRepository: SpringDataH2UserRepository) : UserRepository {

    override fun findAllUsers(): List<User> = h2UserRepository.findAll().map { it.toDomain() }

    override fun findUsername(username: String): User? =
        h2UserRepository.findUserByUsername(username)?.let { it.toDomain() }

    override fun createUser(user: User): User = try {
        h2UserRepository.save(user.toEntity()).toDomain()
    } catch (ex: DataIntegrityViolationException) {
        throw UsernameAlreadyExistsException(user.username)
    }

    override fun findUserById(id: Long): User =
        h2UserRepository.findById(id).getOrElse { throw UserNotFoundException(id) }.toDomain()
}
