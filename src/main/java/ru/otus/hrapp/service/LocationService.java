package ru.otus.hrapp.service;

import ru.otus.hrapp.model.entity.Location;

public interface LocationService {
    Location getLocationById(long locationId);
    boolean existsById(long locationId);
}
