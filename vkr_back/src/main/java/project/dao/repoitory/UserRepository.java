package project.dao.repoitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.dao.entity.UserImpl;
import project.dto.security.AuthenticationRequestDto;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserImpl, Long> {
    Optional<UserImpl> findByLogin(String login);
}
