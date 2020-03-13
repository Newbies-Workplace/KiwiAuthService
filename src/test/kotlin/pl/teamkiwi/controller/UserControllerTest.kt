package pl.teamkiwi.controller

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import pl.teamkiwi.converter.UserConverter
import pl.teamkiwi.exception.EmailOccupiedException
import pl.teamkiwi.model.dto.UserDTO
import pl.teamkiwi.model.request.UserCreateRequest
import pl.teamkiwi.service.UserService

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private val userService = mockk<UserService>()
    private val userConverter = mockk<UserConverter>()
    private val userController = UserController(userService, userConverter)

    @Nested
    inner class CreateUser {

        @Test
        fun `should throw EmailOccupiedException when user with specific email exists`() {
            //given
            val userCreateRequest = mockk<UserCreateRequest>()
            val userDTO = mockk<UserDTO>()

            every { userCreateRequest.email } returns "anyEmail@ok.ok"
            every { userService.findByEmail(any()) } returns userDTO

            //when
            assertThrows<EmailOccupiedException> {
                userController.createUser(userCreateRequest)
            }
        }
    }
}