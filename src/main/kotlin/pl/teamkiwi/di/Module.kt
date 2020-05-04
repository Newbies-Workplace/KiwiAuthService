package pl.teamkiwi.di

import com.typesafe.config.ConfigFactory
import org.koin.dsl.module
import pl.jutupe.DatabaseConfiguration
import pl.teamkiwi.application.controller.AuthController
import pl.teamkiwi.application.controller.UserController
import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.service.AuthService
import pl.teamkiwi.domain.service.UserService
import pl.teamkiwi.infrastructure.repository.UserExposedRepository
import pl.teamkiwi.infrastructure.security.BCryptPasswordEncoder

val mainModule = module {
    single { AuthController(get()) }
    single { AuthService(get(), get()) }

    single { UserController(get()) }
    single { UserService(get(), get()) }
    single { UserExposedRepository() as UserRepository }

    single { BCryptPasswordEncoder() as PasswordEncoder }

    single {
        DatabaseConfiguration(
            url = ConfigFactory.load().getString("kiwi.database.url"),
            driver = ConfigFactory.load().getString("kiwi.database.driver"),
            user = ConfigFactory.load().getString("kiwi.database.user"),
            password = ConfigFactory.load().getString("kiwi.database.password"))
    }
}