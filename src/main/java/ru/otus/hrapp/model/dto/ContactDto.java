package ru.otus.hrapp.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeContactType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDto {

    @Positive(message = "Contact ID must be positive")
    private int id;

    @NotNull(message = "Contact type cannot be null.")
    private EmployeeContactType type;

    @NotNull(message = "Account name cannot be null.")
    private String accountName;
    private String description;

    @Positive(message = "Employee ID must be positive")
    private int employeeId;
}
