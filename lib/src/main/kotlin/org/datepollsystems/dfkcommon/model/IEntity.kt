package org.datepollsystems.dfkcommon.model

import java.util.*

interface IEntity : ITimestamp {
    var id: Long
    var version: Long
    override fun hashCode(): Int
    override fun equals(other: Any?): Boolean
    fun touch()
}

interface ITimestamp {
    val createdAt: Date?
    var updatedAt: Date?
}

interface IEntityWithName : IEntity {
    var name: String
}