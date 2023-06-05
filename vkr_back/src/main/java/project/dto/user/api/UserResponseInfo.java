package project.dto.user.api;


import project.dao.entity.api.User;
import project.dto.common.StatusImpl;

import java.util.List;


public interface UserResponseInfo {
    /**
     * @return статус ответа
     */
    StatusImpl getStatus();

    /**
     * @return найденные пользователи
     */
    List<User> getUserList();
}
