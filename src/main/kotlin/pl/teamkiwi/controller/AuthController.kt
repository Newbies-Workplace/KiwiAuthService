package pl.teamkiwi.controller

import io.ktor.auth.Principal
import pl.teamkiwi.converter.toUserPrincipal
import pl.teamkiwi.exception.UnauthorizedException
import pl.teamkiwi.model.request.UserLoginRequest
import pl.teamkiwi.security.AuthSession
import pl.teamkiwi.security.PasswordEncoder
import pl.teamkiwi.service.UserService
import java.util.*

class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(userLoginRequest: UserLoginRequest): AuthSession {
        val foundUser = userService.findByEmail(userLoginRequest.email) ?: throw UnauthorizedException()
        val isValidPassword = passwordEncoder.isValid(userLoginRequest.password, foundUser.passwordHash)
        if (!isValidPassword) { throw UnauthorizedException() }

        return AuthSession(
            foundUser.id
        )
    }

    fun validate(session: AuthSession): Principal? =
        session.userId.let {
            try {
                userService.findById(UUID.fromString(it))
            } catch (e: Exception) {
                null
            }
        }?.toUserPrincipal()
}