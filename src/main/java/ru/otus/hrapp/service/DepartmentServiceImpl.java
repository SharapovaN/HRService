package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.entity.Department;
import ru.otus.hrapp.repository.DepartmentRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

@Slf4j
@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department getDepartmentById(long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("No department is found for the id: " + departmentId));
    }
}
