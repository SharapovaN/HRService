package ru.otus.hrapp.service;

import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ProjectDto;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public List<ProjectDto> getAllProjects() {
        return null;
    }

    @Override
    public List<ProjectDto> getProjectByEmployeeId(long employeeId) {
        return null;
    }

    @Override
    public List<ProjectDto> getProjectByOwnerId(long ownerId) {
        return null;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        return null;
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        return null;
    }
}
