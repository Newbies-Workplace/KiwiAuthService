package pl.teamkiwi.service

import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.dto.create.UserCreateDTO
import pl.teamkiwi.repository.UserRepository
import java.util.*

class UserService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String): UserDTO? {
        return userRepository.findByEmail(email)
    }

    fun findById(id: UUID): UserDTO? {
        return userRepository.findById(id)
    }

    fun save(userCreateDTO: UserCreateDTO): UserDTO {
        return userRepository.save(userCreateDTO)
    }
}