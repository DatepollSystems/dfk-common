package org.datepollsystems.dfkcommon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@JsonPropertyOrder("id")
@MappedSuperclass
abstract class AEntity : IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Persisting should work nonetheless
    override var id: Long = -1

    @JsonIgnore
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override val createdAt: Date? = null

    @JsonIgnore
    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override var updatedAt: Date? = null

    @JsonIgnore
    @Version
    override var version: Long = 0

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AEntity

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

abstract class ATimestamps : ITimestamp {
    @JsonIgnore
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override val createdAt: Date? = null

    @JsonIgnore
    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override var updatedAt: Date? = null
}