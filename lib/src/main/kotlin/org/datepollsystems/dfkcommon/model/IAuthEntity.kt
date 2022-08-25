package org.datepollsystems.dfkcommon.model

interface IAuthEntity : IEntityWithLongId {
    val authIdentifier: String
}

interface IEntitySession<T : IAuthEntity> : IEntityWithLongId {
    val token: String
    var description: String
    var entity: T
}

interface IAuthActivatableEntity : IAuthEntity {
    val activated: Boolean get() = true
}

interface IAuthPasswordEntity : IAuthActivatableEntity {
    var passwordHash: String
    var forcePasswordChange: Boolean
}