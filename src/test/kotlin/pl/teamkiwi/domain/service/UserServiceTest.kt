package pl.teamkiwi.domain.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.model.entity.User
import pl.teamkiwi.domain.model.exception.EmailOccupiedException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val userService = UserService(userRepository, passwordEncoder)

    @Nested
    inner class CreateUser {

        @Test
        fun `should throw EmailOccupiedException when user with specific email already exists`() {
            //given
            val email = "anyEmail@ok.ok"
            val foundUser = mockk<User>()

            every { userRepository.findByEmail(email) } returns foundUser

            //when
            assertThrows<EmailOccupiedException> {
                userService.createUser(email, "pass")
            }
        }
    }
}

internal fun createTestUser(
    id: String = "67b44030-4448-11ea-b77f-2e728ce88125",
    email: String = "email@co.pl",
    passwordHash: String = "ahbsiboa"
) = User(
    id, email, passwordHash
)