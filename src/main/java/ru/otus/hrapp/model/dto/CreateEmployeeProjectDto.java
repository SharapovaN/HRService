package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeProjectRole;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeProjectDto {

    @Positive(message = "Project ID must be positive")
    private Long projectId;

    @Positive(message = "Employee ID must be positive")
    private Long employeeId;

    @NotNull(message = "Role cannot be null")
    private EmployeeProjectRole employeeProjectRole;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "The start date must be in the future or now.")
    private LocalDate employeeStartDate;

    @Future(message = "The end date must be in the future.")
    private LocalDate employeeEndDate;
}
