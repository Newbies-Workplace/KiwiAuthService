package pl.teamkiwi.domain.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import pl.teamkiwi.domain.`interface`.PasswordEncoder
import pl.teamkiwi.domain.`interface`.UserRepository
import pl.teamkiwi.domain.model.exception.UnauthorizedException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AuthServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val authService = AuthService(userRepository, passwordEncoder)

    @Nested
    inner class Login {

        @Test
        fun `login with valid email and password should return user`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"
            val userDTO = createTestUser(email = validEmail)

            every { userRepository.findByEmail(validEmail) } returns userDTO
            every { passwordEncoder.isValid(validPassword, any()) } returns true

            //when
            val user = authService.login(validEmail, validPassword)

            //then
            assertEquals(userDTO, user)
        }

        @Test
        fun `login with invalid password should throw UnauthorizedException`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"
            val userDTO = createTestUser(email = validEmail)

            every { userRepository.findByEmail(validEmail) } returns userDTO
            every { passwordEncoder.isValid(validPassword, any()) } returns false

            //when
            assertThrows<UnauthorizedException> {
                authService.login(validEmail, validPassword)
            }
        }

        @Test
        fun `login with invalid email should throw UnauthorizedException`() {
            //given
            val validEmail = "email@ok.ok"
            val validPassword = "password"

            every { userRepository.findByEmail(validEmail) } returns null

            //when
            assertThrows<UnauthorizedException> {
                authService.login(validEmail, validPassword)
            }
        }
    }
}