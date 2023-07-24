package ru.otus.hrapp.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.otus.hrapp.model.dto.SaveContactDto;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean checkCreateEmployeeContactPermissions(SaveContactDto saveContactDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) this.getAuthentication().getPrincipal();

        return saveContactDto.getEmployeeId() == userDetails.getEmployeeId() ||
                userDetails.getAuthorities().contains("ROLE_HR_MANAGER");
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
        return this;
    }
}
