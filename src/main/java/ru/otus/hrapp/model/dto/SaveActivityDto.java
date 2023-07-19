package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.ActivityStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveActivityDto {
    private String name;
    private String description;

    @NotNull(message = "Activity status cannot be null")
    private ActivityStatus status;

    @NotNull(message = "Activity start date cannot be null")
    @FutureOrPresent(message = "The start date must be in the future or now.")
    private LocalDate startDate;

    @Future(message = "The end date must be in the future.")
    private LocalDate endDate;
}
