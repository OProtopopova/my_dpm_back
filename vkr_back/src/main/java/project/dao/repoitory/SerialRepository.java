package project.dao.repoitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.dao.entity.SerialImpl;

@Repository
public interface SerialRepository extends CrudRepository<SerialImpl, Long> {
}
