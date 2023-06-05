package project.dao.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dao.security.api.AuthenticationRequestInfoDao;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestInfoDaoImpl implements AuthenticationRequestInfoDao {
    String login;
    String password;
}
