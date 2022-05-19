package org.datepollsystems.dfkcommon.util

import java.security.SecureRandom
import java.util.*

class RandomGenerator {
    companion object {
        private val random: SecureRandom = SecureRandom()
        private val encoder = Base64.getUrlEncoder().withoutPadding()

        fun string(size: Int = 20): String {
            val buffer = ByteArray(size)
            random.nextBytes(buffer)
            return encoder.encodeToString(buffer)
        }
    }
}