package ru.otus.hrapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeContactType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee_contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private EmployeeContactType type;
    private String accountName;
    private String description;
    private int employeeId;
}
