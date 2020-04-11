package pl.teamkiwi.infrastructure.repository.table

import pl.teamkiwi.infrastructure.repository.table.Constants.DEFAULT_CHARSET

/**
 * Database table
 */
object Users : StringIdTable() {
    val email = varchar("email", EMAIL_MAX_LENGTH, DEFAULT_CHARSET).uniqueIndex()
    val passwordHash = varchar("passwordHash", PASSWORD_HASH_MAX_LENGTH, DEFAULT_CHARSET)
}

const val EMAIL_MAX_LENGTH = 50
const val PASSWORD_HASH_MAX_LENGTH = 60

const val PASSWORD_MAX_LENGTH = 20