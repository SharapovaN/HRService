package ru.otus.hrapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String middleName;
    private String email;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private LocalDate hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "employee_id", updatable = false)
    private List<Contact> contacts;

    public Employee(String name, String surname, String middleName, String email, LocalDate birthday,
                    EmployeeStatus status, LocalDate hireDate) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.email = email;
        this.birthday = birthday;
        this.status = status;
        this.hireDate = hireDate;
    }
}
