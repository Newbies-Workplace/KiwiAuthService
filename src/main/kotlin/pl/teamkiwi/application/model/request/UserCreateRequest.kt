package pl.teamkiwi.application.model.request

import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import pl.teamkiwi.infrastructure.repository.table.EMAIL_MAX_LENGTH
import pl.teamkiwi.infrastructure.repository.table.PASSWORD_MAX_LENGTH

data class UserCreateRequest(
    val email: String,
    val password: String
) {

    init {
        validate(this) {
            validate(UserCreateRequest::email)
                .isNotBlank()
                .isEmail()
                .hasSize(max = EMAIL_MAX_LENGTH)
            validate(UserCreateRequest::password)
                .isNotBlank()
                .hasSize(max = PASSWORD_MAX_LENGTH)
        }
    }
}