package demo.jpa.postgres.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 * to prevent infinit cycle deserializing Employee/Manager(child/parent), uee the jackson 2.x
 * JsonIdentifyInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
 *     - provide the id value, and Jackson will use it as-is.
 * JsonIdentifyInfo(generator = ObjectIdGenerators.IntSequece.class, property="@id") - using basic int sequence
 */
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {

    @Id
    @GeneratedValue
    Long id;
    String lastName;
    String firstName;
    String role;

    @ManyToOne
    Manager manager;

    private Employee() {}

    public Employee(String lastName, String firstName, String role, Manager manager) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.manager = manager;
    }
}
