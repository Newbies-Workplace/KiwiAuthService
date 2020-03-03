package pl.teamkiwi

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.session
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.header
import org.koin.core.logger.PrintLogger
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.slf4j.event.Level
import pl.teamkiwi.di.mainModule
import pl.teamkiwi.exception.BadRequestException
import pl.teamkiwi.exception.EmailOccupiedException
import pl.teamkiwi.exception.UnauthorizedException
import pl.teamkiwi.repository.Exposed
import pl.teamkiwi.repository.Users
import pl.teamkiwi.router.authRoutes
import pl.teamkiwi.router.userRoutes
import pl.teamkiwi.security.AUTH_SESSION_KEY
import pl.teamkiwi.security.AuthPrincipal
import java.util.*

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        commandLineEnvironment(args)
    ).start()
}

@Suppress("unused") // Referenced in application.conf
fun Application.mainModule() {
    install(Koin) {
        logger(PrintLogger())

        modules(
            listOf(mainModule)
        )
    }

    install(CallLogging) {
        level = Level.INFO
    }

    install(Exposed) {
        connectWithConfig(get())

        createSchemas(
            Users
        )
    }

    install(CORS) {
        anyHost()
    }

    install(Sessions) {
        header<AuthPrincipal>(AUTH_SESSION_KEY, SessionStorageMemory()) {
            identity { UUID.randomUUID().toString() }
        }
    }

    install(Authentication) {

        session<AuthPrincipal> {
            challenge { call.respond(HttpStatusCode.Unauthorized) }

            //as long as we don't support roles etc. we do not need to validate session
            validate { it }
        }
    }

    install(ContentNegotiation) {
        jackson {}
    }

    install(StatusPages) {
        exception<BadRequestException> { call.respond(HttpStatusCode.BadRequest) }
        exception<EmailOccupiedException> { call.respond(HttpStatusCode.Conflict) }
        exception<UnauthorizedException> { call.respond(HttpStatusCode.Unauthorized) }
    }

    routing {
        authRoutes()
        userRoutes()
    }
}