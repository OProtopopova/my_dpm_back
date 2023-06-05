package project.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dao.entity.UserImpl;
import project.dao.repoitory.UserRepository;
import project.dao.security.JwtTokenProvider;
import project.dao.security.SecurityRole;
import project.dao.security.SecurityStatus;
import project.dto.common.StatusImpl;
import project.dto.security.AuthenticationRequestDto;
import project.dto.user.UserDto;
import project.dto.user.UserResponseInfoImpl;
import project.dto.user.api.UserResponseInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Map<Object, Object> authenticate(@NonNull AuthenticationRequestDto requestDto) {
        log.debug(String.format("Полученный ДТО запрос на аутентификаию пользователя: %s", requestDto));

        UserImpl user = userRepository.findByLogin(requestDto.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не существует"));
        log.debug(String.format("Найденный пользователь: %s",user));
        Map<Object, Object> response = new HashMap<>();
        String token = jwtTokenProvider.createToken(requestDto.getLogin(), user.getRole().name());
        log.debug(String.format("Сгенерированный токен: %s", token));
        response.put("login", requestDto.getLogin());
        response.put("token", token);
        response.put("role",user.getRole());



        log.debug(String.format("Созданный ответ: %s", response));
        return response;
    }

    public UserResponseInfo save(UserDto user) throws Exception {
        try {
            if (userRepository.findByLogin(user.getLogin()).isPresent()) {
                throw new Exception("Логин уже занят");
            }

            UserImpl userEntity = UserImpl.builder()
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .build();

            userEntity.setRole(SecurityRole.USER);
            userEntity.setStatus(SecurityStatus.ACTIVE);
            log.debug(String.format("Сохраняемый пользователь %s",userEntity));
            String userNotEncryptedPassword = userEntity.getPassword();
            log.debug(String.format("Не зашифрованный пароль пользователя: %s", userNotEncryptedPassword));
            userEntity.setPassword(passwordEncoder().encode(userNotEncryptedPassword));
            log.debug(String.format("Закодированный пароль: %s", userEntity.getPassword()));
            UserImpl savedUser = userRepository.save(userEntity);
            savedUser.setPassword(userNotEncryptedPassword);
            log.debug(String.format(
                    "Не зашифрованный пароль пользователя выдаваемый наружу: %s",
                    savedUser.getPassword()
            ));
            log.debug(String.format("Сохраненный пользователь %s", savedUser));
            return UserResponseInfoImpl.builder()
                    .status(StatusImpl.builder().success(true).build())
                    .userList(Collections.singletonList(UserImpl.builder()
                                    .login(savedUser.getLogin())
                                    .email(savedUser.getEmail())
                                    .id(savedUser.getId())
                                    .password(savedUser.getPassword())
                                    .role(savedUser.getRole())
                                    .serialList(savedUser.getSerialList())
                            .build()))
                    .build();
        } catch (Exception e) {
            log.error(String.format("Ошибка сохранения пользователя %s в БД",user));
            return UserResponseInfoImpl.builder()
                    .status(StatusImpl.builder().success(false).description(e.getMessage()).build())
                    .build();
        }
    }

    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
