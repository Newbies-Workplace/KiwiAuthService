package pl.teamkiwi.application.controller

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import pl.teamkiwi.application.converter.toUserResponse
import pl.teamkiwi.application.model.request.UserCreateRequest
import pl.teamkiwi.application.util.myReceive
import pl.teamkiwi.domain.model.exception.BadRequestException
import pl.teamkiwi.domain.service.UserService

class UserController(
    private val userService: UserService
) {

    suspend fun postUser(call: ApplicationCall) {
        val userCreateRequest = call.myReceive<UserCreateRequest>(BadRequestException())

        val createdUser = userService.createUser(userCreateRequest.email, userCreateRequest.password)
        val response = createdUser.toUserResponse()

        call.respond(HttpStatusCode.Created, response)
    }
}