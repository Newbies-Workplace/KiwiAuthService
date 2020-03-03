package pl.teamkiwi.controller

import pl.teamkiwi.exception.UnauthorizedException
import pl.teamkiwi.model.request.UserLoginRequest
import pl.teamkiwi.security.AuthPrincipal
import pl.teamkiwi.security.PasswordEncoder
import pl.teamkiwi.service.UserService

class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(userLoginRequest: UserLoginRequest): AuthPrincipal {
        val foundUser = userService.findByEmail(userLoginRequest.email) ?: throw UnauthorizedException()
        val isValidPassword = passwordEncoder.isValid(userLoginRequest.password, foundUser.passwordHash)
        if (!isValidPassword) { throw UnauthorizedException() }

        return AuthPrincipal(
            foundUser.id
        )
    }
}