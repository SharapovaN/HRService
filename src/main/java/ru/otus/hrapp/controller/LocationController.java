package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.CreateLocationDto;
import com.practice.hrapp.model.dto.LocationDto;
import com.practice.hrapp.service.LocationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Api(tags = "Location Controller")
@RequestMapping("location")
@RestController
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @ApiOperation(
            value = "Get location by ID",
            nickname = "getLocationById")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public LocationDto getLocationById(@ApiParam(name = "locationId", value = "Location unique identifier")
                                       @RequestParam int locationId) {
        return locationService.getLocationById(locationId);
    }

    @ApiOperation(
            value = "Get all locations",
            nickname = "getAllLocations")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("getAllLocations")
    public List<LocationDto> getAllLocations() {
        return locationService.getAllLocations();
    }

    @ApiOperation(
            value = "Get location by country ID",
            nickname = "getLocationsByCountryId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("getByCountryId")
    public List<LocationDto> getLocationsByCountryId(@ApiParam(name = "countryId", value = "Country unique identifier")
                                                     @RequestParam int countryId) {
        return locationService.getLocationsByCountryId(countryId);
    }

    @ApiOperation(
            value = "Create location",
            nickname = "createLocation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 409, message = "Location already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public LocationDto createLocation(@ApiParam(name = "createLocationDto", value = "DTO for location creation")
                                      @RequestBody @Valid CreateLocationDto createLocationDto) {
        return locationService.createLocation(createLocationDto);
    }
}
