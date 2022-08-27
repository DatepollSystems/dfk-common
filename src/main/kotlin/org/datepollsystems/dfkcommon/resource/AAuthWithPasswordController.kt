package org.datepollsystems.dfkcommon.resource

import org.datepollsystems.dfkcommon.auth.AJwtService
import org.datepollsystems.dfkcommon.dto.ISignInWithPasswordChangeDto
import org.datepollsystems.dfkcommon.dto.ISignInWithPasswordDto
import org.datepollsystems.dfkcommon.dto.JWTResponse
import org.datepollsystems.dfkcommon.exceptions.CredentialsIncorrectException
import org.datepollsystems.dfkcommon.exceptions.NoPasswordChangeRequiredException
import org.datepollsystems.dfkcommon.exceptions.PasswordChangeRequiredException
import org.datepollsystems.dfkcommon.model.AEntitySession
import org.datepollsystems.dfkcommon.model.IAuthPasswordEntity
import org.datepollsystems.dfkcommon.service.AEntityAuthWithPasswordService
import org.datepollsystems.dfkcommon.service.AEntitySessionService
import org.springframework.web.bind.annotation.RequestBody

abstract class AAuthWithPasswordController<ENTITYTYPE : IAuthPasswordEntity, SESSIONTYPE : AEntitySession<ENTITYTYPE>>(
    private val entityAuthWithPasswordService: AEntityAuthWithPasswordService<ENTITYTYPE>,
    entitySessionService: AEntitySessionService<ENTITYTYPE, SESSIONTYPE>,
    jwtUtils: AJwtService
) : AAuthActivatableController<ENTITYTYPE, SESSIONTYPE>(entityAuthWithPasswordService, entitySessionService, jwtUtils) {

    override fun filter(entity: ENTITYTYPE) {
        super.filter(entity)

        if (entity.forcePasswordChange) throw PasswordChangeRequiredException()
    }

    protected fun signIn(dto: ISignInWithPasswordDto): JWTResponse {
        val entity = entityAuthService.getByAuthIdentifierOrThrow(dto.authIdentifier)

        if (!entityAuthWithPasswordService.hasCorrectCredentials(entity, dto)) throw CredentialsIncorrectException()

        return checkLoginDetails(entity, dto.stayLoggedIn, dto.sessionInformation)
    }

    protected fun signInWithPasswordChange(@RequestBody dto: ISignInWithPasswordChangeDto): JWTResponse {
        val entity = entityAuthService.getByAuthIdentifierOrThrow(dto.authIdentifier)

        super.filter(entity)

        if (!entity.forcePasswordChange)
            throw NoPasswordChangeRequiredException()

        if (!entityAuthWithPasswordService.hasCorrectCredentials(entity, dto))
            throw CredentialsIncorrectException()

        entity.forcePasswordChange = false
        entityAuthWithPasswordService.updateCredentials(entity, dto.newPassword)

        return checkLoginDetails(entity, dto.stayLoggedIn, dto.sessionInformation)
    }
}