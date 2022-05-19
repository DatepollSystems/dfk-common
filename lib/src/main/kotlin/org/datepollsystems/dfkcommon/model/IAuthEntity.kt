package org.datepollsystems.dfkcommon.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

interface IAuthEntity : IEntity {
    val authIdentifier: String
}

interface IEntitySession<T : IAuthEntity> : IEntity {
    val token: String
    var description: String
    var entity: T

    @JsonProperty("createdAt")
    fun getCreateField(): Date? = createdAt

    @JsonProperty("updatedAt")
    fun getUpdateFiled(): Date? = updatedAt
}

interface IAuthActivatableEntity : IAuthEntity {
    val activated: Boolean get() = true
}

interface IAuthPasswordEntity : IAuthActivatableEntity {
    var passwordHash: String
    var forcePasswordChange: Boolean
}