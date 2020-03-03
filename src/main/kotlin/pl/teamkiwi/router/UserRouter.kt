package pl.teamkiwi.router

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.koin.ktor.ext.inject
import pl.teamkiwi.controller.UserController
import pl.teamkiwi.exception.BadRequestException
import pl.teamkiwi.model.request.UserCreateRequest
import pl.teamkiwi.util.myReceive

fun Routing.userRoutes() {
    val userController by inject<UserController>()

    post("/v1/user") {
        val userCreateRequest = call.myReceive<UserCreateRequest>(BadRequestException())

        val response = userController.createUser(userCreateRequest)

        call.respond(HttpStatusCode.Created, response)
    }
}