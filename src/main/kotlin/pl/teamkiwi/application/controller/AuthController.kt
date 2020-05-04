package pl.teamkiwi.application.controller

import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import pl.teamkiwi.application.model.AUTH_SESSION_KEY
import pl.teamkiwi.application.model.AuthPrincipal
import pl.teamkiwi.application.model.request.UserLoginRequest
import pl.teamkiwi.application.util.myReceive
import pl.teamkiwi.domain.model.exception.BadRequestException
import pl.teamkiwi.domain.model.exception.UnauthorizedException
import pl.teamkiwi.domain.service.AuthService

class AuthController(
    private val authService: AuthService
) {

    suspend fun login(call: ApplicationCall) {
        val loginRequest = call.myReceive<UserLoginRequest>(BadRequestException())

        val user = authService.login(loginRequest.email, loginRequest.password)

        val session = AuthPrincipal(
            user.id
        )

        call.sessions.set(session)
        call.respond("")
    }

    suspend fun logout(call: ApplicationCall) {
        call.sessions.clear(AUTH_SESSION_KEY)
        call.respond("")
    }

    suspend fun getSession(call: ApplicationCall) {
        val session = call.sessions.get(AUTH_SESSION_KEY) ?: throw UnauthorizedException()

        call.respond(session)
    }
}