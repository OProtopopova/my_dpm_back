package project.dto.serial;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import project.dao.entity.api.Serial;
import project.dto.common.StatusImpl;
import project.dto.serial.api.SerialResponseInfo;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerialResponseInfoImpl implements SerialResponseInfo {

    /**
     * поле статуса запроса
     */
    @JsonProperty
    private StatusImpl status;

    /**
     * поле списка пользователей
     */
    @JsonProperty
    private List<Serial> serialList;

    public StatusImpl getStatus() {
        return status;
    }

    public List<Serial> getSerialList() {
        if (serialList == null) {
            serialList = new ArrayList<>();
        }
        return serialList;
    }

}
