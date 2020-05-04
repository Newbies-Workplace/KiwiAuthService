package pl.teamkiwi.domain.`interface`

import pl.teamkiwi.domain.model.entity.User

interface UserRepository {

    fun findByEmail(email: String): User?

    fun save(user: User): User
}