package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.entity.Location;
import ru.otus.hrapp.repository.LocationRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

@Slf4j
@AllArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location getLocationById(long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("No location is found for the id: " + locationId));
    }

    @Override
    public boolean existsById(long locationId) {
        return locationRepository.existsById(locationId);
    }
}
