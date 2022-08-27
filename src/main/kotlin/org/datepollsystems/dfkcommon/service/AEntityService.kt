package org.datepollsystems.dfkcommon.service

import org.datepollsystems.dfkcommon.exceptions.NotFoundException
import org.datepollsystems.dfkcommon.model.IEntity
import org.springframework.data.jpa.repository.JpaRepository

abstract class AEntityService<T : IEntity<ID>, ID : Any>(
    val repository: JpaRepository<T, ID>
) {
    fun existsById(id: ID): Boolean = repository.existsById(id)

    fun existsById(ids: List<ID>): Boolean = getById(ids).size == ids.size

    fun existsByIdOrThrow(id: ID): ID =
        if (!repository.existsById(id))
            throw NotFoundException()
        else id

    fun existsByIdOrThrow(ids: List<ID>): List<ID> =
        if (!existsById(ids))
            throw NotFoundException()
        else ids

    fun getAll(): List<T> = repository.findAll()

    fun getById(id: ID): T? = repository.findById(id).orElse(null)

    fun getById(ids: List<ID>): List<T> = repository.findAllById(ids)

    fun getByIdOrThrow(id: ID): T =
        repository.findById(id).orElseThrow { throw NotFoundException("""${javaClass.simpleName} not found""") }

    fun getByIdOrThrow(ids: List<ID>): List<T> {
        val entities = getById(ids)
        if (entities.size != ids.size) {
            throw NotFoundException()
        }
        return entities
    }

    fun deleteById(id: ID) = repository.deleteById(id)

    fun deleteByIdOrThrow(id: ID) = repository.deleteById(existsByIdOrThrow(id))

    fun deleteAll(entities: List<T>) = repository.deleteAll(entities)

    fun save(entity: T) = repository.save(entity)
}