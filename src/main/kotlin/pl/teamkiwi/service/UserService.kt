package pl.teamkiwi.service

import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.dto.create.UserCreateDTO
import pl.teamkiwi.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String): UserDTO? {
        return userRepository.findByEmail(email)
    }

    fun save(userCreateDTO: UserCreateDTO): UserDTO {
        return userRepository.save(userCreateDTO)
    }
}