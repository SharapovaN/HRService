package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.LocationDto;
import ru.otus.hrapp.model.entity.Location;
import ru.otus.hrapp.repository.LocationRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> getAllLocations() {
        log.info("getAllLocations method was called");

        return locationRepository.findAll().stream()
                .map(ModelConverter::toLocationDto)
                .toList();
    }

    @Override
    public Location getLocationById(long locationId) {
        log.info("getLocationById method was called with id: {}", locationId);

        return locationRepository.findById(locationId).orElseThrow(() ->
                new ResourceNotFoundException("No location is found for the id: " + locationId));
    }

    @Override
    public boolean existsById(long locationId) {
        log.info("existsById method was called with locationId: {}", locationId);

        return locationRepository.existsById(locationId);
    }
}
