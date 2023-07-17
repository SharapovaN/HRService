package ru.otus.hrapp.repository.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.hrapp.model.dto.*;
import ru.otus.hrapp.model.entity.*;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployee(SaveEmployeeDto updateEmployeeDto, @MappingTarget Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContact(ContactDto updateContactDto, @MappingTarget Contact contact);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContract(ContractDto contractDto, @MappingTarget Contract contract);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateActivity(ActivityDto activityDto, @MappingTarget Activity activity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProject(ProjectDto projectDto, @MappingTarget Project project);
}
