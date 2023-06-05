package project.dao.security;

/**
 * класс разрешений на операции
 * @author KhrustalevSA
 * @since 22.01.2023
 */
public enum SecurityPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    SecurityPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
