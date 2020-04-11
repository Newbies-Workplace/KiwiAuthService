package pl.teamkiwi.infrastructure.repository

import org.jetbrains.exposed.dao.EntityID
import pl.teamkiwi.domain.model.entity.User
import pl.teamkiwi.infrastructure.repository.table.StringIdEntity
import pl.teamkiwi.infrastructure.repository.table.StringIdEntityClass
import pl.teamkiwi.infrastructure.repository.table.Users

class UserDAO(id: EntityID<String>) : StringIdEntity(id) {
    companion object : StringIdEntityClass<UserDAO>(Users)

    var email by Users.email
    var passwordHash by Users.passwordHash

    fun toUser() =
        User(
            id = id.toString(),
            email = email,
            passwordHash = passwordHash
        )
}