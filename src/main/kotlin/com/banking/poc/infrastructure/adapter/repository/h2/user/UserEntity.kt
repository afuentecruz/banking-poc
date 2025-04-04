package com.banking.poc.infrastructure.adapter.repository.h2.user

import com.banking.poc.infrastructure.adapter.repository.h2.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
class UserEntity(
    override var id: Long? = null,
    @Column(name = "`username`", unique = true, nullable = false) val username: String,
    @Column(name = "`password`", nullable = false) val password: String
) : BaseEntity(id) {
    companion object
}
