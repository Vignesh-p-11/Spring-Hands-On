
package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByEmpDesg(String empDesg);
    List<Employee> findByEmpSalBetween(Integer min, Integer max);
}
