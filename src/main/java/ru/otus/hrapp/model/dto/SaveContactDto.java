package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeContactType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveContactDto {
    @NotNull(message = "Contact type cannot be null.")
    private EmployeeContactType type;

    @NotNull(message = "Account name cannot be null.")
    private String accountName;
    private String description;

    @Positive(message = "Employee ID must be positive")
    private long employeeId;
}
