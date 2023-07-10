package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
