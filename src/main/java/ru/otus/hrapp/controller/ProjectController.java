package ru.otus.hrapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;
import ru.otus.hrapp.model.dto.SaveProjectDto;
import ru.otus.hrapp.service.ProjectService;

import java.util.List;

@Tag(name = "Project controller")
@CrossOrigin
@AllArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Get all projects")
    @GetMapping("/project")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Operation(summary = "Get projects by employee id")
    @GetMapping("/project/{employeeId}")
    public List<ProjectDto> getProjectByEmployeeId(@PathVariable("employeeId")
                                                   @Parameter(name = "employeeId", description = " Employee unique identifier")
                                                   long employeeId) {
        return projectService.getProjectByEmployeeId(employeeId);
    }

    @Operation(summary = "Get projects by owner id")
    @GetMapping("/project/{ownerId}")
    public List<ProjectDto> getProjectByOwnerId(@PathVariable("ownerId")
                                                @Parameter(name = "ownerId", description = " Employee unique identifier")
                                                long ownerId) {
        return projectService.getProjectByOwnerId(ownerId);
    }

    @Operation(summary = "Create project")
    @PostMapping("/project")
    public ProjectDto createProject(@Parameter(name = "projectDto", description = " DTO for creating project")
                                    @RequestBody @Valid SaveProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @Operation(summary = "Update project")
    @PutMapping("/project")
    public ProjectDto updateProject(@Parameter(name = "projectDto", description = " DTO for updating project")
                                    @RequestBody @Valid ProjectDto projectDto) {
        return projectService.updateProject(projectDto);
    }

    @Operation(summary = "Create link between project and employee")
    @PostMapping("/employee_project")
    public String createEmployeeProject(@Parameter(name = "createEmployeeProjectDto",
            description = " DTO for creating link between project and employee")
                                        @RequestBody @Valid CreateEmployeeProjectDto createEmployeeProjectDto) {
        return projectService.createEmployeeProject(createEmployeeProjectDto);
    }
}
