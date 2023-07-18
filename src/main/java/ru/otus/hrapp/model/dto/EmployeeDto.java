package ru.otus.hrapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private LocalDate birthday;
    private EmployeeStatus status;
    private LocalDate hireDate;
    private EmployeeDto manager;
    private LocationDto location;
    private DepartmentDto department;
    private JobDto job;
    private ContractDto contract;
    private List<ContactDto> contactList;
    private String password;
    //private String roleName;
}
