package pl.teamkiwi.controller

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import pl.teamkiwi.exception.UnauthorizedException
import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.request.UserLoginRequest
import pl.teamkiwi.security.PasswordEncoder
import pl.teamkiwi.service.UserService

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AuthControllerTest {

    private val userService = mockk<UserService>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val authController = AuthController(userService, passwordEncoder)

    @Nested
    inner class Login {

        @Test
        fun `login with valid email and password should return session`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"
            val loginRequest = UserLoginRequest(validEmail, validPassword)
            val userDTO = createTestUser(email = validEmail)

            every { userService.findByEmail(validEmail) } returns userDTO
            every { passwordEncoder.isValid(validPassword, any()) } returns true

            //when
            val session = authController.login(loginRequest)

            //then
            assertEquals(userDTO.id, session.userId)
        }

        @Test
        fun `login with invalid password should throw UnauthorizedException`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"
            val loginRequest = UserLoginRequest(validEmail, validPassword)
            val userDTO = createTestUser(email = validEmail)

            every { userService.findByEmail(validEmail) } returns userDTO
            every { passwordEncoder.isValid(validPassword, any()) } returns false

            //when
            assertThrows<UnauthorizedException> {
                authController.login(loginRequest)
            }
        }

        @Test
        fun `login with invalid email should throw UnauthorizedException`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"
            val loginRequest = UserLoginRequest(validEmail, validPassword)

            every { userService.findByEmail(validEmail) } returns null

            //when
            assertThrows<UnauthorizedException> {
                authController.login(loginRequest)
            }
        }
    }
}

internal fun createTestUser(
    id: String = "67b44030-4448-11ea-b77f-2e728ce88125",
    email: String = "email@co.pl",
    passwordHash: String = "ahbsiboa"
) = UserDTO(
    id, email, passwordHash
)