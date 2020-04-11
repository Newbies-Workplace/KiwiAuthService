package pl.teamkiwi.domain.model.entity

data class User(
    val id: String,
    val email: String,
    val passwordHash: String
)