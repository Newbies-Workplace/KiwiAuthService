package pl.teamkiwi.converter

import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.dto.create.UserCreateDTO
import pl.teamkiwi.model.request.UserCreateRequest
import pl.teamkiwi.model.response.UserResponse
import pl.teamkiwi.repository.UserDAO
import pl.teamkiwi.security.PasswordEncoder

fun UserDAO.toUserDTO() =
    UserDTO(
        id = id.toString(),
        email = email,
        passwordHash = passwordHash
    )

fun UserDTO.toUserResponse() =
    UserResponse(
        id = id,
        email = email
    )

class UserConverter(
    private val passwordEncoder: PasswordEncoder
) {

    fun UserCreateRequest.toUserCreateDTO() =
        UserCreateDTO(
            email = email,
            passwordHash = passwordEncoder.encode(password)
        )
}