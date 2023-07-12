package ru.otus.hrapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hrapp.model.entity.Job;

public interface JobRepository extends CrudRepository<Job, Long> {
}
