package demo.jpa.postgres.repository;

import demo.jpa.postgres.model.Employee;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByLastName(@Param("q") String lastName);
}

/****
 No difference at all between handling the loop of messages yourself or let it to Spring data JPA to do the job.
 I think you just need to loop on the messages yourself and catching the errors youself:

  for(EnitityType entity: list){
     try{
         repository.save(entity);
     } catch (Exception e){
         Do what you want
     }
 }
 ***/

/**
 https://stackoverflow.com/questions/23325413/spring-crudrepository-exceptions
 **/
/**
 * Spring has built-in exception translation mechanism, so that all exceptions thrown by the JPA persistence
 * providers are converted into Spring's DataAccessException - for all beans annotated with @Repository (or configured).
 *
 * There are four main groups -
 *
 * NonTransientDataAccessException
 *     - these are the exceptions where a retry of the same operation would fail
 *       unless the cause of the Exception is corrected.
 *       So if you pass non existing id for example, it will fail unless the id exists in the database.
 *
 * RecoverableDataAccessException
 *      - these are the "opposite" of the previous one
 *      - exceptions which are recoverable
 *      - after some recovery steps. More details in the API docs
 *
 * ScriptException
 *      - SQL related exceptions, when trying to process not well-formed script for example.
 *
 * TransientDataAccessException
 *      - these are the exception when recovery is possible without any explicit step,
 *        e.g. when there is a timeout to the database, you are retrying after few seconds.
 *
 * That said, the ideal place to find documentation about all exceptions - is in the API itself
 * just go through the hierarchy of DataAccessException.
 */
