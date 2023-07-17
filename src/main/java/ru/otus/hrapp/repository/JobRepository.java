package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
