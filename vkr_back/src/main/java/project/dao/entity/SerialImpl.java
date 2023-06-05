package project.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.dao.entity.api.Serial;

import javax.persistence.*;

@Entity
@Table
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SerialImpl implements Serial {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private Long season;
    private Long episode;
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore(value = true)
    private UserImpl user;

}
