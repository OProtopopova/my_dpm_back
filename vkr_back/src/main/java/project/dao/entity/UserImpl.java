package project.dao.entity;

import lombok.*;
import project.dao.entity.api.User;
import project.dao.security.SecurityRole;
import project.dao.security.SecurityStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserImpl implements User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String login;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<SerialImpl> serialList;

    /**
     * роль
     */
    @Column
    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    /**
     * статус пользователя
     */
    @Column
    @Enumerated(EnumType.STRING)
    private SecurityStatus status;

}
