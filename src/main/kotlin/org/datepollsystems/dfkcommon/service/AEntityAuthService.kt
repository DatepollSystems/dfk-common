package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.exceptions.AuthEntityNotFoundException
import org.datepollsystems.dfkcommon.exceptions.CredentialsIncorrectException
import org.datepollsystems.dfkcommon.model.IAuthEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.Authentication

abstract class AEntityAuthService<entityType : IAuthEntity>
(private val authEntityRepository: JpaRepository<entityType, Long>) :
    AEntityWithLongIdService<entityType>(authEntityRepository) {

    abstract fun getByAuthIdentifier(identifier: String): entityType?

    fun getByAuthIdentifierOrThrow(identifier: String): entityType =
        getByAuthIdentifier(identifier) ?: throw CredentialsIncorrectException()

    fun getByAuth(auth: Authentication): entityType =
        authEntityRepository.findById(auth.name.toLong()).orElseThrow { throw AuthEntityNotFoundException() }

    fun getIdByAuth(auth: Authentication): Long {
        val entityId = auth.name.toLong()
        if (authEntityRepository.existsById(entityId))
            return entityId
        else
            throw AuthEntityNotFoundException()
    }
}