package project.dao.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * класс ошибки аутентификации с помощью Jwt токена
 * @author KhrustalevSA
 * @since 22.01.2023
 */
@Getter
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * статус Http запроса
     */
    private HttpStatus httpStatus;

    /**
     * конструктор класса
     * @param msg сообщение об ошибке
     * @param httpStatus статус ответа
     */
    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    /**
     * конструктор класса
     * @param msg сообщение
     * @param cause почему ошибка
     * @param httpStatus статус ответа
     */
    public JwtAuthenticationException(String msg, Throwable cause, HttpStatus httpStatus) {
        super(msg, cause);
        this.httpStatus = httpStatus;
    }

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
