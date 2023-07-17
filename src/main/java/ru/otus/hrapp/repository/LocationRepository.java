package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
