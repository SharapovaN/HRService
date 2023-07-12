package ru.otus.hrapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.ProjectStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDto {

    @Positive(message = "Project ID must be positive")
    private int id;

    private String projectType;
    private String description;
    private ProjectStatus status;

    @FutureOrPresent(message = "The start date must be in the future or now.")
    private LocalDate startDate;

    @Future(message = "The end date must be in the future.")
    private LocalDate endDate;

    private Integer ownerId;
}
