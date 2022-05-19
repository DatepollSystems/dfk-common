package org.datepollsystems.dfkcommon.resource

import org.datepollsystems.dfkcommon.auth.AJwtService
import org.datepollsystems.dfkcommon.dto.ISignInDto
import org.datepollsystems.dfkcommon.dto.JWTResponse
import org.datepollsystems.dfkcommon.dto.LogoutDto
import org.datepollsystems.dfkcommon.dto.RefreshJWTWithSessionTokenDto
import org.datepollsystems.dfkcommon.model.AEntitySession
import org.datepollsystems.dfkcommon.model.IAuthEntity
import org.datepollsystems.dfkcommon.service.AEntityAuthService
import org.datepollsystems.dfkcommon.service.AEntitySessionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

abstract class AAuthController<ENTITYTYPE : IAuthEntity, SESSIONTYPE : AEntitySession<ENTITYTYPE>>(
    val entityAuthService: AEntityAuthService<ENTITYTYPE>,
    private val entitySessionService: AEntitySessionService<ENTITYTYPE, SESSIONTYPE>,
    private val jwtUtils: AJwtService
) {

    protected open fun filter(entity: ENTITYTYPE) {
        return
    }

    protected open fun onSuccessfulSignIn(entity: ENTITYTYPE) {
        return
    }

    protected fun signIn(dto: ISignInDto): JWTResponse = checkLoginDetails(
        entityAuthService.getByAuthIdentifierOrThrow(dto.authIdentifier),
        dto.stayLoggedIn,
        dto.sessionInformation
    )

    protected fun checkLoginDetails(
        entity: ENTITYTYPE,
        stayLoggedIn: Boolean?,
        sessionInformation: String?
    ): JWTResponse {
        filter(entity)

        this.onSuccessfulSignIn(entity)
        if (stayLoggedIn != null && stayLoggedIn == true && sessionInformation != null && sessionInformation.isNotBlank()) {
            val session = entitySessionService.create(
                entitySessionService.generateUniqueRandomToken(),
                sessionInformation,
                entity
            )

            return JWTResponse(token = jwtUtils.generateAuthToken(entity.id), sessionToken = session.token)
        }

        return JWTResponse(token = jwtUtils.generateAuthToken(entity.id))
    }

    @PostMapping("/refresh")
    fun refreshJWTWithSessionToken(@RequestBody @Valid request: RefreshJWTWithSessionTokenDto): JWTResponse {
        val session = entitySessionService.getByToken(request.sessionToken)

        val entity = session.entity
        filter(entity)

        entitySessionService.update(session, request.sessionInformation)

        return JWTResponse(token = jwtUtils.generateAuthToken(entity.id))
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout(@RequestBody @Valid dto: LogoutDto) =
        entitySessionService.deleteById(entitySessionService.getByToken(dto.sessionToken).id)
}