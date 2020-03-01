package pl.teamkiwi.security

data class AuthSession(
    val userId: String
)

const val AUTH_SESSION_KEY = "Authorization"