package org.datepollsystems.dfkcommon.dto

import org.datepollsystems.dfkcommon.model.IEntitySession
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

interface ISignInDto {
    val authIdentifier: String
    val sessionInformation: String?
    val stayLoggedIn: Boolean?
}

interface ISignInWithPasswordDto : ISignInDto {
    val password: String
}

interface ISignInWithPasswordChangeDto : ISignInWithPasswordDto {
    val newPassword: String
    val oldPassword: String
    override val password: String get() = oldPassword
}

data class RefreshJWTWithSessionTokenDto(
    @get:NotBlank var sessionToken: String,
    @get:NotBlank @get:Size(min = 6, max = 60) var sessionInformation: String
)

data class LogoutDto(
    @get:NotBlank var sessionToken: String
)

data class JWTResponse(
    var token: String?,
    var sessionToken: String? = null
)

data class SessionResponse(
    val id: Long,
    val description: String,
    val entityId: Long,
    val createdAt: Date?,
    val updatedAt: Date?
) {
    constructor(session: IEntitySession<*>) : this(
        session.id,
        session.description,
        session.entity.id,
        session.createdAt,
        session.updatedAt
    )
}