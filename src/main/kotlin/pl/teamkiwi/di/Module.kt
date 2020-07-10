package pl.teamkiwi.di

import com.typesafe.config.ConfigFactory
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import org.koin.dsl.module
import pl.jutupe.DatabaseConfiguration
import pl.teamkiwi.application.controller.AuthController
import pl.teamkiwi.application.controller.UserController
import pl.teamkiwi.application.util.getProp
import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.service.AuthService
import pl.teamkiwi.domain.service.UserService
import pl.teamkiwi.infrastructure.repository.UserExposedRepository
import pl.teamkiwi.infrastructure.security.BCryptPasswordEncoder

val mainModule = module {
    @Suppress("EXPERIMENTAL_API_USAGE")
    single { HoconApplicationConfig(ConfigFactory.load()) as ApplicationConfig }

    single { AuthController(get()) }
    single { AuthService(get(), get()) }

    single { UserController(get()) }
    single { UserService(get(), get()) }
    single { UserExposedRepository() as UserRepository }

    single { BCryptPasswordEncoder() as PasswordEncoder }

    single {
        DatabaseConfiguration(
            url = getProp("kiwi.database.url"),
            driver = getProp("kiwi.database.driver"),
            user = getProp("kiwi.database.user"),
            password = getProp("kiwi.database.password"))
    }
}