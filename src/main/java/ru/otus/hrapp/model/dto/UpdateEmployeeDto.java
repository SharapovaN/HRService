package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDto extends SaveEmployeeDto {
    private Long id;

    @NotNull(message = "Status cannot be null")
    private EmployeeStatus status;
}
