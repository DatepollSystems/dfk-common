package org.datepollsystems.dfkcommon.dto

import org.datepollsystems.dfkcommon.model.IEntityWithLongId
import org.datepollsystems.dfkcommon.model.IEntityWithName

data class IdResponse(
    var id: Long
) {
    constructor(entity: IEntityWithLongId) : this(entity.id)
}

data class IdAndNameResponse(
    var id: Long,
    var name: String
) {
    constructor(entity: IEntityWithName) : this(entity.id, entity.name)
}