package org.datepollsystems.dfkcommon.model

import javax.persistence.*

@MappedSuperclass
abstract class AEntitySession<T : IAuthEntity>(
    @Column(nullable = false, unique = true, length = 27)
    override val token: String,

    @Column(nullable = false, length = 60)
    override var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    override var entity: T,

    ) : AEntity(), IEntitySession<T> {
    override fun toString(): String = "Id: '$id', Description: '$description', Entity: '${entity.id}'"
}