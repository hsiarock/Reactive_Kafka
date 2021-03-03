package demo.jpa.postgres.controller;

import demo.jpa.postgres.model.Employee;
import demo.jpa.postgres.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/myemployees")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // get employee by id
    @GetMapping("get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        LOG.info("get REST GET request : get/{id} -> " + employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found this id: " + employeeId));

        return ResponseEntity.ok(employee);
    }

    // Save from POST
    @PostMapping("insert")
    public Employee insertEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    // Update
    @PutMapping("employee_update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        LOG.info("get REST PUT request : employee_update/{id} -> " + employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found this id: " + employeeId));

        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }
}
