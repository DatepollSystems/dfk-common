package org.datepollsystems.dfkcommon.domain

import org.datepollsystems.dfkcommon.exceptions.NotFoundException
import org.datepollsystems.dfkcommon.model.IEntityWithLongId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface IRepository<T : IEntityWithLongId> : JpaRepository<T, Long> {
    fun existsById(ids: List<Long>): Boolean = getById(ids).size == ids.size

    fun existsByIdOrThrow(id: Long): Long =
        if (!existsById(id))
            throw NotFoundException()
        else id

    fun existsByIdOrThrow(ids: List<Long>): List<Long> =
        if (!existsById(ids))
            throw NotFoundException()
        else ids

    fun getAll(): List<T> = findAll()

    fun getByIdOrNull(id: Long): T? = findById(id).orElse(null)

    fun getByIdOrThrow(id: Long): T =
        findById(id).orElseThrow { throw NotFoundException("""${javaClass.simpleName} not found""") }

    fun getById(ids: List<Long>): List<T> = findAllById(ids)

    fun getByIdOrThrow(ids: List<Long>): List<T> {
        val entities = getById(ids)
        if (entities.size != ids.size) {
            throw NotFoundException()
        }
        return entities
    }

    fun deleteByIdOrThrow(id: Long) = deleteById(existsByIdOrThrow(id))
}