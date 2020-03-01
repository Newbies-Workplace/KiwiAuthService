package pl.teamkiwi.di

import com.typesafe.config.ConfigFactory
import org.koin.dsl.module
import pl.teamkiwi.controller.AuthController
import pl.teamkiwi.controller.UserController
import pl.teamkiwi.converter.UserConverter
import pl.teamkiwi.repository.DatabaseConfiguration
import pl.teamkiwi.repository.UserRepository
import pl.teamkiwi.security.PasswordEncoder
import pl.teamkiwi.service.UserService

val mainModule = module {
    single { PasswordEncoder() }

    single { AuthController(get(), get()) }

    single { UserController(get(), get()) }
    single { UserService(get()) }
    single { UserRepository() }
    single { UserConverter(get()) }

    single {
        DatabaseConfiguration(
            url = ConfigFactory.load().getString("kiwi.database.url"),
            driver = ConfigFactory.load().getString("kiwi.database.driver"),
            user = ConfigFactory.load().getString("kiwi.database.user"),
            password = ConfigFactory.load().getString("kiwi.database.password"))
    }
}