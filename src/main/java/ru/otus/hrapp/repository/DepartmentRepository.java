package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
