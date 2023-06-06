package project.dto.serial.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import project.dao.entity.SerialImpl;
import project.dao.entity.api.Serial;
import project.dto.common.StatusImpl;

import java.util.List;

public interface SerialResponseInfo {
     StatusImpl getStatus();


     List<SerialImpl> getSerialList();
}
