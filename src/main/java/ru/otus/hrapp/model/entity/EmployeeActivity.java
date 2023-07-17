package ru.otus.hrapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeActivityRole;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee_activity")
public class EmployeeActivity {

    @EmbeddedId
    private EmployeeActivityID employeeActivityID;

    @Enumerated(EnumType.STRING)
    private EmployeeActivityRole activityRole;
}
