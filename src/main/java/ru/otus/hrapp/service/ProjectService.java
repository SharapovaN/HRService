package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;
import ru.otus.hrapp.model.dto.SaveProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();

    List<ProjectDto> getProjectByEmployeeId(long employeeId);

    List<ProjectDto> getProjectByOwnerId(long ownerId);

    ProjectDto createProject(SaveProjectDto projectDto);

    ProjectDto updateProject(ProjectDto projectDto);

    String createEmployeeProject(CreateEmployeeProjectDto createEmployeeProjectDto);

    boolean isEmployeeProjectOwner(long employeeId, long projectId);
}
