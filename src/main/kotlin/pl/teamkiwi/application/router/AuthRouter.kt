package pl.teamkiwi.application.router

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.koin.ktor.ext.inject
import pl.teamkiwi.application.controller.AuthController

fun Routing.authRoutes() {
    val authController by inject<AuthController>()

    post("v1/login") {
        authController.login(call)
    }

    authenticate {
        get("v1/logout") {
            authController.logout(call)
        }

        get("v1/session") {
            authController.getSession(call)
        }
    }
}