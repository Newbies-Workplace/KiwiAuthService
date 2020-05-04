package pl.teamkiwi.application.router

import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.koin.ktor.ext.inject
import pl.teamkiwi.application.controller.UserController

fun Routing.userRoutes() {
    val userController by inject<UserController>()

    post("/v1/user") {
        userController.postUser(call)
    }
}