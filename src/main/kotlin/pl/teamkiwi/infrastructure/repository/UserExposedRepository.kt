package pl.teamkiwi.infrastructure.repository

import org.jetbrains.exposed.sql.transactions.transaction
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.model.entity.User
import pl.teamkiwi.infrastructure.repository.table.Users

class UserExposedRepository : UserRepository {

    override fun findByEmail(email: String): User? =
        transaction {
            UserDAO.find { Users.email eq email }
                .firstOrNull()
                ?.toUser()
        }

    override fun save(user: User): User =
        transaction {
            UserDAO.new(user.id) {
                email = user.email
                passwordHash = user.passwordHash
            }.toUser()
        }
}