package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.exceptions.NotFoundException
import org.datepollsystems.dfkcommon.model.IEntity
import org.springframework.data.jpa.repository.JpaRepository

abstract class AEntityService<T : IEntity>(
    val repository: JpaRepository<T, Long>
) {
    fun existsById(id: Long): Boolean = repository.existsById(id)

    fun existsById(ids: List<Long>): Boolean = getById(ids).size == ids.size

    fun existsByIdOrThrow(id: Long): Long =
        if (!repository.existsById(id))
            throw NotFoundException()
        else id

    fun existsByIdOrThrow(ids: List<Long>): List<Long> =
        if (!existsById(ids))
            throw NotFoundException()
        else ids

    fun getAll(): List<T> = repository.findAll()

    fun getById(id: Long): T? = repository.findById(id).orElse(null)

    fun getById(ids: List<Long>): List<T> = repository.findAllById(ids)

    fun getByIdOrThrow(id: Long): T =
        repository.findById(id).orElseThrow { throw NotFoundException("""${javaClass.simpleName} not found""") }

    fun getByIdOrThrow(ids: List<Long>): List<T> {
        val entities = getById(ids)
        if (entities.size != ids.size) {
            throw NotFoundException()
        }
        return entities
    }

    fun deleteById(id: Long) = repository.deleteById(id)

    fun deleteByIdOrThrow(id: Long) = repository.deleteById(existsByIdOrThrow(id))

    fun deleteAll(entities: List<T>) = repository.deleteAll(entities)
}