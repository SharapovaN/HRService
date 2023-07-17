package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    private Long id;

    @Positive(message = "Salary must be positive")
    private Integer salary;

    @FutureOrPresent(message = "The start date must be in the future or now.")
    private LocalDate startDate;

    @Future(message = "The end date must be in the future.")
    private LocalDate endDate;

    @Past(message = "The last revision date must be in the past.")
    private LocalDate lastRevisionDate;

    private String description;
}
