package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.LocationDto;
import ru.otus.hrapp.model.entity.Location;

import java.util.List;

public interface LocationService {
    List<LocationDto> getAllLocations();

    Location getLocationById(long locationId);

    boolean existsById(long locationId);
}
