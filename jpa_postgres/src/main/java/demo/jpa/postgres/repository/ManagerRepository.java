package demo.jpa.postgres.repository;

import demo.jpa.postgres.model.Manager;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ManagerRepository extends CrudRepository<Manager, Long> {
    List<Manager> findByEmployeesRoleContains(@Param("q") String role);
    List<Manager> findByEmployeesFirstName(@Param("q") String firstName);
    List<Manager> findDistinctByEmployeesFirstName(@Param("q") String firstName);
}
