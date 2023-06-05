package project.dao.entity.api;

import lombok.ToString;
import project.dao.entity.SerialImpl;
import project.dao.security.SecurityRole;
import project.dao.security.SecurityStatus;

import javax.persistence.*;
import java.util.List;

public interface User {

     Long getId();

     String getLogin();
     String getPassword();
     String getEmail();

     List<SerialImpl> getSerialList();

     SecurityRole getRole();

     SecurityStatus getStatus();
}
