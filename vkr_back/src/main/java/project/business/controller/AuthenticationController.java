package project.business.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import project.business.service.UserService;
import project.dto.common.StatusImpl;
import project.dto.security.AuthenticationRequestDto;
import project.dto.user.UserDto;
import project.dto.user.UserResponseInfoImpl;
import project.dto.user.api.UserResponseInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/authentication")
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    /**
     * сервис работы с пользователями
     */
    private final UserService userService;

    /**
     * Менеджер сравнивает логин и пароль пришедший из Http запроса с логином и паролем
     * хранящемся в базе данных с помощью метода authenticate()
     */
    private final AuthenticationManager authenticationManager;

    /**
     * контроллер с автоопределением
     * @param authenticationManager манеджер аутентификации
     */
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * метод аутентификации пользователя
     * @param request запрос с логином и паролем пользователя
     * @return тело ответа
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getLogin(),
                    request.getPassword())
            );
            ResponseEntity<?> authenticatedResponse = ResponseEntity.ok(userService.authenticate(request));
            log.debug("Созданное тело ответа: %s");
            return authenticatedResponse;
        } catch (Throwable e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    /**
     * метод выхода пользователя из аккаунта
     * @param request
     * @param response
     */
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    /**
     * метод регистрации пользователя
     * @param user объект сохраняемого пользователя
     * @return информация о сохраненном пользователе
     */
    @PostMapping("/registration")
    public UserResponseInfo registration(@RequestBody UserDto user) {
        try {
            return userService.save(user);
        } catch (Throwable e) {
            log.error(String.format("Ошибка сохранения пользователя %s", user));
            log.error(e.getMessage(), e.getCause());
            return UserResponseInfoImpl.builder()
                    .status(StatusImpl.builder().success(false).description(e.getMessage()).build())
                    .build();
        }
    }
}
