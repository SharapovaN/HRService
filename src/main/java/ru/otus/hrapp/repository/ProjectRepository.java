package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hrapp.model.entity.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = """
            SELECT * FROM project pr
            JOIN employee_project ep ON pr.id = ep.project_id 
            WHERE ep.employee_id = :employeeId
            """, nativeQuery = true)
    List<Project> findByEmployeeId(@Param("employeeId") long employeeId);

    List<Project> findProjectsByOwnerId(@Param("ownerId") long ownerId);
}
