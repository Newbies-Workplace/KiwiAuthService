package pl.teamkiwi.router

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.koin.ktor.ext.inject
import pl.teamkiwi.controller.AuthController
import pl.teamkiwi.exception.BadRequestException
import pl.teamkiwi.exception.UnauthorizedException
import pl.teamkiwi.model.request.UserLoginRequest
import pl.teamkiwi.security.AUTH_SESSION_KEY
import pl.teamkiwi.util.myReceive

fun Routing.authRoutes() {
    val authController by inject<AuthController>()

    post("v1/login") {
        val loginRequest = call.myReceive<UserLoginRequest>(BadRequestException())

        val session = authController.login(loginRequest)

        call.sessions.set(session)
        call.respond("")
    }

    authenticate {
        get("v1/logout") {
            call.sessions.clear(AUTH_SESSION_KEY)
            call.respond("")
        }

        get("v1/session") {
            val session = call.sessions.get(AUTH_SESSION_KEY)

            session?.let {
                call.respond(it)
            } ?: throw UnauthorizedException()
        }
    }
}