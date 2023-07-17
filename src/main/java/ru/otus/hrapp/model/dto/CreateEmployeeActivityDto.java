package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeActivityRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeActivityDto {

    @Positive(message = "Employee ID must be positive")
    private Long employeeId;

    @Positive(message = "Activity ID must be positive")
    private Long activityId;

    @NotNull(message = "Activity role cannot be null")
    private EmployeeActivityRole activityRole;
}
