package org.datepollsystems.dfkcommon.resource

import org.datepollsystems.dfkcommon.auth.AJwtService
import org.datepollsystems.dfkcommon.exceptions.AccountNotActivatedException
import org.datepollsystems.dfkcommon.model.AEntitySession
import org.datepollsystems.dfkcommon.model.IAuthActivatableEntity
import org.datepollsystems.dfkcommon.service.AEntityAuthService
import org.datepollsystems.dfkcommon.service.AEntitySessionService

abstract class AAuthActivatableController<ENTITYTYPE : IAuthActivatableEntity, SESSIONTYPE : AEntitySession<ENTITYTYPE>>(
    entityAuthService: AEntityAuthService<ENTITYTYPE>,
    entitySessionService: AEntitySessionService<ENTITYTYPE, SESSIONTYPE>,
    jwtUtils: AJwtService
) : AAuthController<ENTITYTYPE, SESSIONTYPE>(entityAuthService, entitySessionService, jwtUtils) {
    override fun filter(entity: ENTITYTYPE) {
        if (!entity.activated) throw AccountNotActivatedException()

        super.filter(entity)
    }
}