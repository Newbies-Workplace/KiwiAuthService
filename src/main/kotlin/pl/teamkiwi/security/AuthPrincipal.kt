package pl.teamkiwi.security

import io.ktor.auth.Principal

/**
 * Class that defines session and principal.
 */
data class AuthPrincipal(
    val userId: String
) : Principal

const val AUTH_SESSION_KEY = "Authorization"