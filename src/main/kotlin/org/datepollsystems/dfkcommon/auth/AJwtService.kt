package org.datepollsystems.dfkcommon.auth

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*

abstract class AJwtService {
    open val signToken: String = "placeholder"
    open val validForInMilliseconds: Long = 3_600_000 // 1. hour

    protected var jwtParser: JwtParser? = null
    protected var jwtBuilder: JwtBuilder? = null

    open fun getAuthTokenEntityId(jwtToken: String): String {
        if (this.jwtParser == null) {
            if (signToken == "placeholder") throw IllegalArgumentException("Sign token was not overwritten")

            this.jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey(signToken)).build()
        }
        return getTokenBody(jwtToken)
    }

    open fun generateAuthToken(entityId: Long): String {
        if (this.jwtBuilder == null) {
            if (signToken == "placeholder") throw IllegalArgumentException("Sign token was not overwritten")

            jwtBuilder = Jwts.builder().signWith(getSigningKey(signToken), SignatureAlgorithm.HS512)
        }
        return generateToken(entityId.toString(), Date(System.currentTimeMillis() + validForInMilliseconds))
    }

    protected fun getSigningKey(secret: String): Key {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    protected fun generateToken(subject: String, expirationDate: Date): String {
        val builder = jwtBuilder!!
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date())
            .setSubject(subject)
            .setExpiration(expirationDate)

        return builder.compact()
    }

    protected fun getTokenBody(jwtToken: String): String {
        return jwtParser!!.parseClaimsJws(jwtToken).body.subject
    }
}