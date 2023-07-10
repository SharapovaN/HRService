package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.CreateProjectDto;
import com.practice.hrapp.model.dto.EmployeeDto;
import com.practice.hrapp.model.dto.EmployeeProjectDto;
import com.practice.hrapp.model.dto.ProjectDto;
import com.practice.hrapp.service.EmployeeProjectService;
import com.practice.hrapp.service.EmployeeService;
import com.practice.hrapp.service.ProjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Project Controller")
@RequestMapping("project")
@RestController
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeProjectService employeeProjectService;
    private final EmployeeService employeeService;

    @Autowired
    public ProjectController(ProjectService projectService, EmployeeProjectService employeeProjectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeProjectService = employeeProjectService;
        this.employeeService = employeeService;
    }

    @ApiOperation(
            value = "Get project by ID",
            nickname = "getProject")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ProjectDto getProjectById(@ApiParam(name = "projectId", value = "Project unique identifier")
                                     @RequestParam int projectId) {
        return projectService.getProjectById(projectId);
    }

    @ApiOperation(
            value = "Get project staff",
            nickname = "getProjectStaff")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("getProjectStaff")
    public List<EmployeeDto> getProjectStaff(@ApiParam(name = "projectId", value = "Project unique identifier")
                                             @RequestParam int projectId,
                                             @ApiParam(name = "isActiveOnly", value = " Request parameter to search for ACTIVE employees only (default) or for all employees")
                                             @RequestParam(required = true, defaultValue = "true") boolean isActiveOnly) {
        return employeeService.getEmployeesByProjectId(projectId, isActiveOnly);
    }

    @ApiOperation(
            value = "Create project",
            nickname = "createProject")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('HR_MANAGER') or hasRole('PROJECT_MANAGER')")
    public ProjectDto createProject(@ApiParam(name = "projectDto", value = "DTO for creating project")
                                    @RequestBody @Valid CreateProjectDto createProjectDto) {
        return projectService.createProject(createProjectDto);
    }

    @ApiOperation(
            value = "Update project",
            nickname = "updateProject")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public ProjectDto updateProject(@ApiParam(name = "updateProjectDto", value = "DTO for updating project")
                                    @RequestBody @Valid ProjectDto projectDto) {
        return projectService.updateProject(projectDto);
    }

    @ApiOperation(
            value = "Assign an employee to a project",
            nickname = "assignEmployee")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 409, message = "Contact already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("assignEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('HR_MANAGER') or hasRole('PROJECT_MANAGER')")
    public EmployeeProjectDto assignEmployee(@ApiParam(name = "createEmployeeProjectDto", value = "DTO for assigning an employee to a project")
                                             @RequestBody @Valid EmployeeProjectDto createEmployeeProjectDto) {
        return employeeProjectService.createEmployeeProject(createEmployeeProjectDto);
    }
}
