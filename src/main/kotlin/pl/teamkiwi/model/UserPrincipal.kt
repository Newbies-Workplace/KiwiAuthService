package pl.teamkiwi.model

import io.ktor.auth.Principal

data class UserPrincipal(
    val id: String,
    val email: String
): Principal