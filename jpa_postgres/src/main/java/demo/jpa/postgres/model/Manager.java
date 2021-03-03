package demo.jpa.postgres.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Manager {
    @Id
    @GeneratedValue Long id;
    String name;

    @OneToMany(mappedBy = "manager")
    List<Employee> employees;

    private Manager() {}

    public Manager(String name) {
        this.name = name;
    }
}
