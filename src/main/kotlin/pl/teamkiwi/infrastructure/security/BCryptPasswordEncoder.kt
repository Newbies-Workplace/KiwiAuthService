package pl.teamkiwi.infrastructure.security

import at.favre.lib.crypto.bcrypt.BCrypt
import pl.teamkiwi.domain.`interface`.PasswordEncoder

class BCryptPasswordEncoder : PasswordEncoder {

    override fun encode(password: String): String =
        BCrypt.withDefaults()
            .hashToString(PASSWORD_HASH_COST, password.toCharArray())

    override fun isValid(password: String, hash: String): Boolean =
        BCrypt.verifyer()
            .verify(password.toCharArray(), hash.toCharArray()).verified

    companion object {
        private const val PASSWORD_HASH_COST = 12
    }
}