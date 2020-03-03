package pl.teamkiwi.repository

import org.jetbrains.exposed.sql.transactions.transaction
import pl.teamkiwi.converter.toUserDTO
import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.dto.create.UserCreateDTO

class UserRepository {

    fun findByEmail(email: String): UserDTO? =
        transaction {
            UserDAO.find { Users.email eq email }
                .firstOrNull()
                ?.toUserDTO()
        }

    fun save(user: UserCreateDTO): UserDTO =
        transaction {
            UserDAO.new {
                email = user.email
                passwordHash = user.passwordHash
            }.toUserDTO()
        }
}