package pl.teamkiwi.repository

import org.jetbrains.exposed.dao.UUIDTable

/**
 * Database table
 */
object Users : UUIDTable() {
    val email = varchar("email", EMAIL_MAX_LENGTH).uniqueIndex()
    val passwordHash = varchar("passwordHash", PASSWORD_HASH_MAX_LENGTH)
}

const val EMAIL_MAX_LENGTH = 50
const val PASSWORD_HASH_MAX_LENGTH = 60

const val PASSWORD_MAX_LENGTH = 20