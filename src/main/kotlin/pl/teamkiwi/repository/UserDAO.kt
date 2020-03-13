package pl.teamkiwi.repository

import org.jetbrains.exposed.dao.EntityID
import pl.teamkiwi.repository.table.StringIdEntity
import pl.teamkiwi.repository.table.StringIdEntityClass
import pl.teamkiwi.repository.table.Users

class UserDAO(id: EntityID<String>) : StringIdEntity(id) {
    companion object : StringIdEntityClass<UserDAO>(Users)

    var email by Users.email
    var passwordHash by Users.passwordHash
}