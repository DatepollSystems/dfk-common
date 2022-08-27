package org.datepollsystems.dfkcommon.domain

import org.datepollsystems.dfkcommon.model.IAuthEntity
import org.datepollsystems.dfkcommon.model.IEntitySession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import java.util.*

@NoRepositoryBean
interface IEntitySessionRepository<EntityType : IAuthEntity, SessionType : IEntitySession<EntityType>> :
    JpaRepository<SessionType, Long> {

    @Query("select s from #{#entityName} s where s.token = :token")
    fun findByToken(@Param("token") token: String): SessionType?

    @Query("select s from #{#entityName} s where s.entity.id = :eId")
    fun findByEntityId(@Param("eId") entityId: Long): List<SessionType>

    @Query("select s from #{#entityName} s where s.updatedAt <= :updatedDateTime")
    fun findByUpdatedDateTimeBefore(
        @Param("updatedDateTime") updatedDateTime: Date
    ): List<SessionType>

    @Query("select count(s)>0 from #{#entityName} s where s.token = :token")
    fun existsByToken(@Param("token") token: String): Boolean

    @Query("select count(s)>0 from #{#entityName} s where s.id = :id and s.entity.id = :eId")
    fun existsByIdAndEntityId(@Param("id") id: Long, @Param("eId") entityId: Long): Boolean
}