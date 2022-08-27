package org.datepollsystems.dfkcommon.domain

import org.datepollsystems.dfkcommon.model.IAuthEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface IEntityAuthRepository<entityType : IAuthEntity> : JpaRepository<entityType, Long>