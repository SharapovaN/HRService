package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hrapp.model.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLocationId(long id);

    Employee findByEmail(String email);

    @Query(value = "SELECT * FROM employee WHERE location_id = :locationId AND status = 'ACTIVE'",
            nativeQuery = true)
    List<Employee> findActiveEmployeeByLocationId(@Param("locationId") long locationId);

    @Query(value = "SELECT * FROM employee WHERE name LIKE :searchPhrase OR surname LIKE :searchPhrase OR email LIKE :searchPhrase AND status = 'ACTIVE'",
            nativeQuery = true)
    List<Employee> findActiveEmployeeBySearchPhrase(@Param("searchPhrase") String searchPhrase);

    @Query(value = "SELECT * FROM employee WHERE name LIKE :searchPhrase OR surname LIKE :searchPhrase OR email LIKE :searchPhrase",
            nativeQuery = true)
    List<Employee> findAllEmployeeBySearchPhrase(@Param("searchPhrase") String searchPhrase);

    @Query(value = "SELECT * FROM employee WHERE department_id = :departmentId AND location_id = :locationId",
            nativeQuery = true)
    List<Employee> findEmployeeByDepartmentAndLocation(@Param("departmentId") long departmentId,
                                                       @Param("locationId") long locationId);

    @Modifying
    @Query(value = "UPDATE employee SET status = :status WHERE id = :id",
            nativeQuery = true)
    void updateEmployeeStatus(@Param("status") String status, @Param("id") long id);

    @Query(value = "SELECT * FROM employee WHERE hire_date < :date AND status = 'PENDING'",
            nativeQuery = true)
    List<Employee> findPendingEmployeeHireDateBefore(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM employee WHERE hire_date IN (:dates) AND status = 'PENDING'",
            nativeQuery = true)
    List<Employee> findPendingEmployeeHireDateIs(@Param("dates") List<LocalDate> dates);

    @Query(value = "SELECT * FROM employee emp JOIN employee_project ep ON emp.id = ep.employee_id WHERE ep.project_id = :projectId",
            nativeQuery = true)
    List<Employee> findByProjectId(@Param("projectId") long projectId);

    @Query(value = "SELECT * FROM employee emp JOIN employee_project ep ON emp.id = ep.employee_id WHERE ep.project_id = :projectId AND emp.status = 'ACTIVE'",
            nativeQuery = true)
    List<Employee> findActiveEmployeesByProjectId(@Param("projectId") long projectId);

}
