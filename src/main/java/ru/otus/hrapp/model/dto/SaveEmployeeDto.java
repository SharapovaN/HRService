package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.RoleType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveEmployeeDto {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Surname cannot be null")
    private String surname;

    private String middleName;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Past(message = "The birthday must be in the past.")
    @NotNull(message = "Birthday cannot be null")
    private LocalDate birthday;

    @FutureOrPresent(message = "The hire date must be in the future or now.")
    @NotNull(message = "Hire date cannot be null")
    private LocalDate hireDate;

    @Positive(message = "Manager ID must be positive")
    private Long managerId;

    @Positive(message = "Location ID must be positive")
    private Long locationId;

    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    @Positive(message = "Job ID must be positive")
    private Long jobId;

    @Positive(message = "Salary must be positive.")
    private Integer salary;

    @FutureOrPresent(message = "The contract start date must be in the future or now.")
    private LocalDate contractStartDate;

    @Future(message = "The contract end date must be in the future or now.")
    private LocalDate contractEndDate;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Username cannot be null")
    private RoleType role;
}
