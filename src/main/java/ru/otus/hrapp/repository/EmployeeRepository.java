package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hrapp.model.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query(value = """
            SELECT * FROM employee WHERE status = 'ACTIVE'
            """, nativeQuery = true)
    List<Employee> findEmployeesByStatusActive();

    @Modifying
    @Query(value = """
            UPDATE employee SET status = :status WHERE id = :id
            """, nativeQuery = true)
    void updateEmployeeStatus(@Param("status") String status, @Param("id") long id);
}
