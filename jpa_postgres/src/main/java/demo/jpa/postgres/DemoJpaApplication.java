package demo.jpa.postgres;

import demo.jpa.postgres.model.Employee;
import demo.jpa.postgres.model.Manager;
import demo.jpa.postgres.repository.EmployeeRepository;
import demo.jpa.postgres.repository.ManagerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(EmployeeRepository employeeRepository,
							   ManagerRepository managerRepository) {
		return args -> {
			Manager m = managerRepository.save(new Manager("Connie"));
			Manager m2 = managerRepository.save(new Manager("Alex"));

			employeeRepository.save(new Employee("Hsia", "David", "role1", m));
			employeeRepository.save(new Employee("Gandolf", "David", "role2", m2));
			employeeRepository.save(new Employee("Hsia", "Jean", "role3", m));
			employeeRepository.save(new Employee("Holmer", "David", "role3", m));
		};
	}
}

