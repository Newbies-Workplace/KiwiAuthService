package pl.teamkiwi.model.dto

data class UserDTO(
    val id: String,
    val email: String,
    val passwordHash: String
)