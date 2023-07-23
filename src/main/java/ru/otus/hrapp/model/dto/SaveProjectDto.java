package ru.otus.hrapp.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.ProjectStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProjectDto {
    private String projectType;
    private ProjectStatus status;

    @FutureOrPresent(message = "The start date must be in the future or now.")
    private LocalDate startDate;

    @Future(message = "The end date must be in the future.")
    private LocalDate endDate;

    private Long ownerId;
}
