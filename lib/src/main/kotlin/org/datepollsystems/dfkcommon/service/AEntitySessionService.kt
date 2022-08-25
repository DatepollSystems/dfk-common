package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.domain.IEntitySessionRepository
import org.datepollsystems.dfkcommon.exceptions.SessionTokenIncorrectException
import org.datepollsystems.dfkcommon.model.AEntitySession
import org.datepollsystems.dfkcommon.model.IAuthEntity
import org.datepollsystems.dfkcommon.util.RandomGenerator
import java.util.*

abstract class AEntitySessionService<
        entityType : IAuthEntity,
        sessionType : AEntitySession<entityType>>
    (val entitySessionRepository: IEntitySessionRepository<entityType, sessionType>) :
    AEntityWithLongIdService<sessionType>(entitySessionRepository) {

    fun getByToken(token: String) = entitySessionRepository.findByToken(token) ?: throw SessionTokenIncorrectException()

    fun getByEntityId(entityId: Long) = entitySessionRepository.findByEntityId(entityId)

    fun existsByToken(token: String) = entitySessionRepository.existsByToken(token)

    fun existsByIdAndEntityId(id: Long, entityId: Long) = entitySessionRepository.existsByIdAndEntityId(id, entityId)

    fun generateUniqueRandomToken(): String {
        var token: String
        while (true) {
            token = RandomGenerator.string()

            if (!entitySessionRepository.existsByToken(token)) {
                break
            }
        }
        return token
    }

    abstract fun create(token: String, description: String, entity: entityType): sessionType

    fun update(entity: sessionType, description: String): sessionType {
        entity.description = description
        entity.touch()
        return entitySessionRepository.save(entity)
    }

    fun clearSessionsOlderThan(past: Date): List<sessionType> {
        val sessions = entitySessionRepository.findByUpdatedDateTimeBefore(past)
        this.deleteAll(sessions)
        return sessions
    }
}