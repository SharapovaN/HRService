package ru.otus.hrapp.repository.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.dto.ContractDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.entity.Contact;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.entity.Project;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployee(SaveEmployeeDto updateEmployeeDto, @MappingTarget Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContact(ContactDto updateContactDto, @MappingTarget Contact contact);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContract(ContractDto contractDto, @MappingTarget Contract contract);
}