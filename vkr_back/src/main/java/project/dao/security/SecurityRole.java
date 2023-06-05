package project.dao.security;

import org.assertj.core.util.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * перечисление ролей пользователей
 * @author KhrustalevSA
 * @since 22.01.2023
 */
public enum SecurityRole {

    /**
     * права пользователя
     */
    USER(Lists.newArrayList(SecurityPermission.USER_READ)),

    /**
     * права администратора
     */
    ADMIN(Lists.newArrayList(SecurityPermission.USER_WRITE, SecurityPermission.USER_READ));

    /**
     * список разрешений
     */
    private final List<SecurityPermission> permissions;

    SecurityRole(List<SecurityPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SecurityPermission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
    }
}
