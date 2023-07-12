package ru.otus.hrapp.util;

import org.springframework.util.CollectionUtils;
import ru.otus.hrapp.model.dto.*;
import ru.otus.hrapp.model.entity.*;

import java.util.List;

public class ModelConverter {

    public static ContractDto toContractDto(Contract contract) {
        ContractDto contractDto = new ContractDto();

        contractDto.setId(contract.getId());
        contractDto.setDescription(contract.getDescription());
        contractDto.setSalary(contract.getSalary());
        contractDto.setStartDate(contract.getStartDate());
        contractDto.setEndDate(contract.getEndDate());
        contractDto.setLastRevisionDate(contract.getLastRevisionDate());

        return contractDto;
    }

    public static JobDto toJobDto(Job job) {
        JobDto jobDto = new JobDto();

        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());

        return jobDto;
    }

    public static ContactDto toContactDto(Contact contact) {
        ContactDto contactDto = new ContactDto();

        contactDto.setAccountName(contact.getAccountName());
        contactDto.setType(contact.getType());
        contactDto.setDescription(contact.getDescription());

        return contactDto;
    }

    public static DepartmentDto toDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName().name());

        return departmentDto;
    }

    public static LocationDto toLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();

        locationDto.setId(location.getId());
        locationDto.setCity(location.getCity());
        locationDto.setPostalCode(location.getPostalCode());
        locationDto.setStreetAddress(location.getStreetAddress());
        locationDto.setStateProvince(location.getStateProvince());

        return locationDto;
    }

    public static EmployeeDto toEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setSurname(employee.getSurname());
        employeeDto.setMiddleName(employee.getMiddleName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setStatus(employee.getStatus());

        return employeeDto;
    }

    public static EmployeeDto toExtendedEmployeeDto(Employee employee, List<Contact> contactList) {
        EmployeeDto employeeDto = toEmployeeDto(employee);

        employeeDto.setBirthday(employee.getBirthday());
        employeeDto.setHireDate(employee.getHireDate());
        employeeDto.setJob(ModelConverter.toJobDto(employee.getJob()));
        employeeDto.setDepartment(ModelConverter.toDepartmentDto(employee.getDepartment()));
        employeeDto.setLocation(ModelConverter.toLocationDto(employee.getLocation()));
        employeeDto.setContract(ModelConverter.toContractDto(employee.getContract()));
        if (employee.getManager() != null) {
            employeeDto.setManager(toEmployeeDto(employee.getManager()));
        }
        if (!CollectionUtils.isEmpty(contactList)) {
            List<ContactDto> contactDtoList = contactList.stream().map((ModelConverter::toContactDto))
                    .toList();
            employeeDto.setContactList(contactDtoList);
        }

        return employeeDto;
    }

    public static EmployeeProjectDto toEmployeeProjectDto(EmployeeProject employeeProject) {
        EmployeeProjectDto employeeProjectDto = new EmployeeProjectDto();

        employeeProjectDto.setEmployeeId(employeeProject.getEmployeeProjectID().getEmployeeId());
        employeeProjectDto.setProjectId(employeeProject.getEmployeeProjectID().getProjectId());
        employeeProjectDto.setEmployeeStartDate(employeeProject.getEmployeeStartDate());
        employeeProjectDto.setEmployeeEndDate(employeeProject.getEmployeeEndDate());
        employeeProjectDto.setEmployeeProjectRole(employeeProject.getEmployeeProjectRole());

        return employeeProjectDto;
    }
}
