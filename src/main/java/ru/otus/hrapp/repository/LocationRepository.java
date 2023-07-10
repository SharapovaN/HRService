package ru.otus.hrapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hrapp.model.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
}
