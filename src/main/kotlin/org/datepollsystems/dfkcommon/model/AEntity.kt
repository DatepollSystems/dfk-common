package org.datepollsystems.dfkcommon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonPropertyOrder("id")
@MappedSuperclass
abstract class AEntityWithLongId : AEntity<Long>(), IEntityWithLongId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = -1
}

@JsonPropertyOrder("id")
@MappedSuperclass
abstract class AEntity<ID : Serializable> : ATimestamps(), IEntity<ID> {
    @JsonIgnore
    @Version
    override var version: Long = 0

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IEntity<*>

        return id == other.id
    }

    /**
     * Update an entity without changing its data
     */
    override fun touch() {
        this.updatedAt = Date()
    }

    override fun toString(): String {
        return """Id: "$id""""
    }
}