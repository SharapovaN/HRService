package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
