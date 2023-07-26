package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;
import ru.otus.hrapp.model.dto.SaveProjectDto;
import ru.otus.hrapp.model.entity.EmployeeProject;
import ru.otus.hrapp.model.entity.EmployeeProjectID;
import ru.otus.hrapp.model.entity.Project;
import ru.otus.hrapp.repository.EmployeeProjectRepository;
import ru.otus.hrapp.repository.ProjectRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    @Override
    public List<ProjectDto> getAllProjects() {
        log.info("getAllProjects method was called");

        return projectRepository.findAll().stream()
                .map(ModelConverter::toProjectDto)
                .toList();
    }

    @Override
    public List<ProjectDto> getProjectByEmployeeId(long employeeId) {
        log.info("getProjectByEmployeeId method was called with employeeId : {}", employeeId);

        return projectRepository.findByEmployeeId(employeeId).stream()
                .map(ModelConverter::toProjectDto)
                .toList();
    }

    @Override
    public List<ProjectDto> getProjectByOwnerId(long ownerId) {
        log.info("getProjectByOwnerId method was called with ownerId : {}", ownerId);

        return projectRepository.findProjectsByOwnerId(ownerId).stream()
                .map(ModelConverter::toProjectDto)
                .toList();
    }

    @Override
    public ProjectDto createProject(SaveProjectDto projectDto) {
        log.info("createProject method was called with projectDto : {}", projectDto);

        var project = new Project();
        project.setProjectType(projectDto.getProjectType());
        project.setArea(projectDto.getArea());
        project.setStatus(projectDto.getStatus());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setOwnerId(projectDto.getOwnerId());

        return ModelConverter.toProjectDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        log.debug("updateProject method was called with projectDto: " + projectDto.getId());

        Optional<Project> projectOptional = projectRepository.findById(projectDto.getId());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            setProjectFields(project, projectDto);
            return ModelConverter.toProjectDto(projectRepository.save(project));
        } else {
            throw new ResourceNotFoundException("No project is found for the id: " + projectDto.getId());
        }
    }

    @Override
    public String createEmployeeProject(CreateEmployeeProjectDto createEmployeeProjectDto) {
        log.debug("createEmployeeProject method was called with createEmployeeProjectDto: " + createEmployeeProjectDto);

        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setEmployeeProjectID(new EmployeeProjectID(createEmployeeProjectDto.getProjectId(),
                createEmployeeProjectDto.getEmployeeId()));
        employeeProject.setEmployeeProjectRole(createEmployeeProjectDto.getEmployeeProjectRole());
        employeeProject.setEmployeeStartDate(createEmployeeProjectDto.getEmployeeStartDate());
        employeeProject.setEmployeeEndDate(createEmployeeProjectDto.getEmployeeEndDate());

        employeeProjectRepository.save(employeeProject);

        return "EmployeeProject successfully created";
    }

    @Override
    public boolean isEmployeeProjectOwner(long employeeId, long projectId) {
        return projectRepository.isEmployeeProjectOwner(employeeId, projectId);
    }

    private void setProjectFields(Project project, ProjectDto projectDto) {
        project.setProjectType(projectDto.getProjectType());
        project.setArea(projectDto.getArea());
        project.setStatus(projectDto.getStatus());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setOwnerId(projectDto.getOwnerId());
    }
}
