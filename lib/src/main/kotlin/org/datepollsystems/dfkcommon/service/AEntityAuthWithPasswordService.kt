package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.dto.ISignInWithPasswordDto
import org.datepollsystems.dfkcommon.model.IAuthPasswordEntity
import org.springframework.data.jpa.repository.JpaRepository

abstract class AEntityAuthWithPasswordService<entityType : IAuthPasswordEntity>
    (private val authEntityRepository: JpaRepository<entityType, Long>) :
    AEntityAuthService<entityType>(authEntityRepository) {

    abstract fun updateCredentials(entity: entityType, credentials: String)

    abstract fun hasCorrectCredentials(encodedPassword: String, passwordToCheck: String): Boolean

    fun hasCorrectCredentials(entity: entityType, dto: ISignInWithPasswordDto) =
        hasCorrectCredentials(entity.passwordHash, dto.password)
}