package pl.teamkiwi.domain.service

import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.model.entity.User
import pl.teamkiwi.domain.model.exception.UnauthorizedException

class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(email: String, password: String): User {
        val user = userRepository.findByEmail(email) ?: throw UnauthorizedException()

        val isValidPassword = passwordEncoder.isValid(password, user.passwordHash)

        if (!isValidPassword) {
            throw UnauthorizedException()
        }

        return user
    }
}