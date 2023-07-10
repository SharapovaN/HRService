package ru.otus.hrapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeProjectRole;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee_project")
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectID employeeProjectID;

    @Enumerated(EnumType.STRING)
    private EmployeeProjectRole employeeProjectRole;

    private LocalDate employeeStartDate;
    private LocalDate employeeEndDate;
}
