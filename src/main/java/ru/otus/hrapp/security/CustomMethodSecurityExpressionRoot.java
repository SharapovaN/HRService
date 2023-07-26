package ru.otus.hrapp.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.otus.hrapp.model.dto.CreateEmployeeProjectDto;
import ru.otus.hrapp.model.dto.ProjectDto;
import ru.otus.hrapp.model.dto.SaveContactDto;
import ru.otus.hrapp.service.ProjectService;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final ProjectService projectService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, ProjectService projectService) {
        super(authentication);
        this.projectService = projectService;
    }

    public boolean checkCreateEmployeeContactPermissions(SaveContactDto saveContactDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) this.getAuthentication().getPrincipal();

        return saveContactDto.getEmployeeId() == userDetails.getEmployeeId() ||
                userDetails.getAuthorities().contains("ROLE_HR_MANAGER");
    }

    public boolean checkCreateEmployeeProjectPermissions(CreateEmployeeProjectDto createEmployeeProjectDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) this.getAuthentication().getPrincipal();

        return projectService.isEmployeeProjectOwner(userDetails.getEmployeeId(), createEmployeeProjectDto.getProjectId());
    }

    public boolean checkUpdateProjectPermissions(ProjectDto projectDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) this.getAuthentication().getPrincipal();

        return projectService.isEmployeeProjectOwner(userDetails.getEmployeeId(), projectDto.getId());
    }


    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
