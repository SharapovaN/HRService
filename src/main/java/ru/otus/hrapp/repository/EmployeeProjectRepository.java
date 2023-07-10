package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.EmployeeProject;
import ru.otus.hrapp.model.entity.EmployeeProjectID;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectID> {
}
