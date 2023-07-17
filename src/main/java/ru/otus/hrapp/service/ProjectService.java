package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();

    List<ProjectDto> getProjectByEmployeeId(long employeeId);

    List<ProjectDto> getProjectByOwnerId(long ownerId);

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(ProjectDto projectDto);

    String createEmployeeProject(CreateEmployeeProjectDto createEmployeeProjectDto);
}
