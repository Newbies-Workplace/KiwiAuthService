package pl.teamkiwi.model.dto.create

data class UserCreateDTO(
    val email: String,
    val passwordHash: String
)