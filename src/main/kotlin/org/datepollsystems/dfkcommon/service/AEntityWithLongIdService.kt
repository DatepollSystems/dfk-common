package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.model.IEntityWithLongId
import org.springframework.data.jpa.repository.JpaRepository

abstract class AEntityWithLongIdService<T : IEntityWithLongId>(
    repository: JpaRepository<T, Long>
) : AEntityService<T, Long>(repository)