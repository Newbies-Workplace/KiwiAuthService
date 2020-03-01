package pl.teamkiwi.util

import io.ktor.application.ApplicationCall
import io.ktor.request.receive

suspend inline fun <reified T : Any> ApplicationCall.safeReceiveOrNull(): T? =
    try {
        receive()
    } catch (e: Exception) {
        null
    }