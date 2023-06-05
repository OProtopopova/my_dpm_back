package project.dao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.dao.entity.UserImpl;
import project.dao.repoitory.UserRepository;

/**
 * класс поиска пользователя для аутентификации
 * @author KhrustalevSA
 * @since 22.01.2023
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserImpl user = repository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь с таким логином не найден")
        );
        return SecurityUser.fromUser(user);
    }
}
