package org.datepollsystems.dfkcommon.model

interface IEntityWithLongId : IEntity<Long>

interface IEntity<ID: Any> : ITimestamps {
    var id: ID
    var version: Long
    override fun hashCode(): Int
    override fun equals(other: Any?): Boolean
    fun touch()
}

interface IEntityWithName : IEntityWithLongId {
    var name: String
}