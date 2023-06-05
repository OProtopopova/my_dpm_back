package project.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * класс запроса аутентификации пользователя
 * @author KhrustalevSA
 * @since 22.01.2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {

    /**
     * логин пользователя
     */
    private String login;

    /**
     * пароль пользователя
     */
    private String password;
}
