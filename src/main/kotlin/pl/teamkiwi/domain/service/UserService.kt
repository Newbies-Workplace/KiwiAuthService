package pl.teamkiwi.domain.service

import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.model.entity.User
import pl.teamkiwi.domain.model.exception.EmailOccupiedException
import java.util.*

class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(email: String, password: String): User {
        if (userRepository.findByEmail(email) != null) {
            throw EmailOccupiedException()
        }

        val passwordHash = passwordEncoder.encode(password)

        val user = User(
            id = UUID.randomUUID().toString(),
            email = email,
            passwordHash = passwordHash
        )

        return userRepository.save(user)
    }
}