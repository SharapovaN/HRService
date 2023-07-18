package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hrapp.model.entity.EmployeeActivity;
import ru.otus.hrapp.model.entity.EmployeeActivityID;

@Repository
public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, EmployeeActivityID> {
}
