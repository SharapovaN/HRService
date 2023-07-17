package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hrapp.model.entity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "SELECT * FROM activity act JOIN employee_activity ea ON act.id = ea.activity_id WHERE ea.employee_id = :employeeId",
            nativeQuery = true)
    List<Activity> findActivitiesByEmployeeId(@Param("employeeId") long employeeId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE activity SET status = :status WHERE id = :id",
            nativeQuery = true)
    void updateActivityStatus(@Param("status") String status, @Param("id") long id);

}
