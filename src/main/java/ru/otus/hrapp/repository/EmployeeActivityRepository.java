package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.EmployeeActivity;
import ru.otus.hrapp.model.entity.EmployeeActivityID;

public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, EmployeeActivityID> {
}
