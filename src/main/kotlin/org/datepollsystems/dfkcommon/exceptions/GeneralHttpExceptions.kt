package org.datepollsystems.dfkcommon.exceptions

import org.springframework.http.HttpStatus

open class NotFoundException(property: String = "Property not found", codeName: String? = null) : HttpException(
    HttpStatus.NOT_FOUND,
    property,
    codeName
)

open class EntityAlreadyExistsException(property: String = "Entity already exists", codeName: String? = null) :
    HttpException(
        HttpStatus.CONFLICT,
        property,
        codeName
    )

open class ForbiddenException(property: String = "Forbidden", codeName: String? = null) : HttpException(
    HttpStatus.FORBIDDEN,
    property,
    codeName
)

open class UnauthorizedException(property: String = "Not authorized", codeName: String? = null) : HttpException(
    HttpStatus.UNAUTHORIZED,
    property,
    codeName
)

open class BadRequestException(property: String = "Bad request", codeName: String? = null) : HttpException(
    HttpStatus.BAD_REQUEST,
    property,
    codeName
)

open class HttpException(
    val httpCode: HttpStatus,
    val property: String,
    var codeName: String? = null
) : Exception(property) {
    init {
        if (codeName == null) {
            codeName = httpCode.name
        }
    }
}