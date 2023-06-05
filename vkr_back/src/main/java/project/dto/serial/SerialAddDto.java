package project.dto.serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerialAddDto {
    private Long episode;
    private Long season;
    private String title;
    private String login;
    private Long userId;
}
