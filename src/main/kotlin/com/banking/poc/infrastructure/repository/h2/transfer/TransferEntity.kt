package com.banking.poc.infrastructure.repository.h2.transfer

import com.banking.poc.infrastructure.repository.h2.BaseEntity
import com.banking.poc.infrastructure.repository.h2.user.UserEntity
import com.banking.poc.infrastructure.repository.h2.wallet.WalletEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "`transfer`")
class TransferEntity(
    override var id: Long? = null,
    @Column(name = "`status`", nullable = false) val status: String,
    @Column(name = "`currency`", nullable = false) val currency: String,
    @Column(name = "`amount`", nullable = false) val amount: BigDecimal,
    @ManyToOne(cascade = [CascadeType.MERGE]) val origin: WalletEntity,
    @ManyToOne(cascade = [CascadeType.MERGE]) val destination: WalletEntity,
    @ManyToOne val generatedBy: UserEntity? = null
) : BaseEntity(id = id) {
    companion object
}
