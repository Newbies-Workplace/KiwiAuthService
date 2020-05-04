package pl.teamkiwi.domain.`interface`

interface PasswordEncoder {

    fun encode(password: String): String

    fun isValid(password: String, hash: String): Boolean
}