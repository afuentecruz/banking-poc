package com.banking.poc.infrastructure.adapter.repository.h2.user

import com.banking.poc.domain.model.user.User

fun User.toEntity(): UserEntity =
    UserEntity(id = this.id, username = this.username, password = this.password)

fun UserEntity.toDomain(): User =
    User(id = this.id, username = this.username, password = this.password)
