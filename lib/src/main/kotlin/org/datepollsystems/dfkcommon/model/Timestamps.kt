package org.datepollsystems.dfkcommon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
abstract class ATimestamps : ITimestamps {
    @JsonIgnore
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override lateinit var createdAt: Date

    @JsonIgnore
    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    override lateinit var updatedAt: Date
}

interface ITimestamps {
    var createdAt: Date
    var updatedAt: Date
}