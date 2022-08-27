package org.datepollsystems.dfkcommon.dto

data class PaginatedResponseDto<T>(
    val numberOfItems: Long,
    val numberOfPages: Int,
    val list: List<T>
)