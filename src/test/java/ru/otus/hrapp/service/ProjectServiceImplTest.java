package ru.otus.hrapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;
import ru.otus.hrapp.model.dto.SaveProjectDto;
import ru.otus.hrapp.model.entity.EmployeeProject;
import ru.otus.hrapp.model.entity.EmployeeProjectID;
import ru.otus.hrapp.model.entity.Project;
import ru.otus.hrapp.model.enumeration.EmployeeProjectRole;
import ru.otus.hrapp.model.enumeration.ProjectStatus;
import ru.otus.hrapp.repository.EmployeeProjectRepository;
import ru.otus.hrapp.repository.ProjectRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeProjectRepository employeeProjectRepository;

    @Test
    void getAllProjects() {
        given(projectRepository.findAll()).willReturn(List.of(new Project(), new Project()));

        var projects = projectService.getAllProjects();
        Assertions.assertEquals(2, projects.size());
    }

    @Test
    void getProjectByEmployeeIdIfOk() {
        given(projectRepository.findByEmployeeId(1)).willReturn(List.of(new Project(1L, "projectType", "description",
                ProjectStatus.ACTIVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 1L)));

        List<ProjectDto> employeeProjects = projectService.getProjectByEmployeeId(1);
        Assertions.assertEquals(1, employeeProjects.size());
        Assertions.assertEquals(1, employeeProjects.get(0).getId());
        Assertions.assertEquals("projectType", employeeProjects.get(0).getProjectType());
        Assertions.assertEquals(ProjectStatus.ACTIVE, employeeProjects.get(0).getStatus());
        Assertions.assertEquals(1, employeeProjects.get(0).getOwnerId());
    }

    @Test
    void getProjectByEmployeeIdIfNotOk() {
        given(projectRepository.findByEmployeeId(1)).willReturn(new ArrayList<>());

        List<ProjectDto> employeeProjects = projectService.getProjectByEmployeeId(1);
        Assertions.assertTrue(employeeProjects.isEmpty());
    }

    @Test
    void getProjectByOwnerIdIfOk() {
        given(projectRepository.findProjectsByOwnerId(1)).willReturn(List.of(new Project(1L, "projectType", "description",
                ProjectStatus.ACTIVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 1L)));

        List<ProjectDto> ownerProjects = projectService.getProjectByOwnerId(1);
        Assertions.assertEquals(1, ownerProjects.size());
        Assertions.assertEquals(1, ownerProjects.get(0).getId());
        Assertions.assertEquals("projectType", ownerProjects.get(0).getProjectType());
        Assertions.assertEquals(ProjectStatus.ACTIVE, ownerProjects.get(0).getStatus());
        Assertions.assertEquals(1, ownerProjects.get(0).getOwnerId());
    }

    @Test
    void getProjectByOwnerIdIfNotOk() {
        given(projectRepository.findProjectsByOwnerId(1)).willReturn(new ArrayList<>());

        List<ProjectDto> ownerProjects = projectService.getProjectByOwnerId(1);
        Assertions.assertTrue(ownerProjects.isEmpty());
    }

    @Test
    void createProject() {
        given(projectRepository.save(new Project(null, "projectType", "description",
                ProjectStatus.ACTIVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 1L)))
                .willReturn(new Project(1L, "projectType", "description",
                        ProjectStatus.ACTIVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 1L));

        ProjectDto projectDto = projectService.createProject(new SaveProjectDto("projectType", "description",
                ProjectStatus.ACTIVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 1L));

        ProjectDto savedProject = projectService.createProject(projectDto);

        Assertions.assertNotNull(savedProject);
        Assertions.assertEquals(1, savedProject.getId());
        Assertions.assertEquals(ProjectStatus.ACTIVE, savedProject.getStatus());
        Assertions.assertEquals(1, savedProject.getOwnerId());
    }

    @Test
    void updateProjectIfNotExists() {
        given(projectRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                projectService.updateProject(new ProjectDto(1L)));
    }

    @Test
    void createEmployeeProject() {
        var employeeProject = new EmployeeProject(new EmployeeProjectID(1L, 1L),
                EmployeeProjectRole.MANAGER, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        given(employeeProjectRepository.save(employeeProject)).willReturn(employeeProject);

        String response = projectService.createEmployeeProject(new CreateEmployeeProjectDto(1L, 1L,
                EmployeeProjectRole.MANAGER, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5)));
        Assertions.assertEquals("EmployeeProject successfully created", response);
    }
}