package com.banking.poc.infrastructure.adapter.repository.h2.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataH2UserRepository : JpaRepository<UserEntity, Long> {
    fun findUserByUsername(username: String): UserEntity?
}

