package ru.otus.hrapp.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.otus.hrapp.service.ProjectService;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final ProjectService projectService;

    public CustomMethodSecurityExpressionHandler(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
                                                                              MethodInvocation invocation) {
        return new CustomMethodSecurityExpressionRoot(authentication, projectService);
    }
}
