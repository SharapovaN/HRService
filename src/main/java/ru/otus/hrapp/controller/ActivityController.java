package ru.otus.hrapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.ActivityDto;
import ru.otus.hrapp.model.dto.CreateEmployeeActivityDto;
import ru.otus.hrapp.model.dto.SaveActivityDto;
import ru.otus.hrapp.service.ActivityService;

import java.util.List;

@Tag(name = "Activity controller")
@CrossOrigin
@AllArgsConstructor
@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "Get all activities")
    @GetMapping("/activity")
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @Operation(summary = "Get activity by id")
    @GetMapping("/activity/{activityId}")
    public ActivityDto getActivityById(@PathVariable("activityId")
                                       @Parameter(name = "activityId", description = " Activity unique identifier")
                                       long activityId) {
        return activityService.getActivityById(activityId);
    }

    @Operation(summary = "Get activity by employee id")
    @GetMapping("/activity/{employeeId}")
    public List<ActivityDto> getActivitiesByEmployeeId(@PathVariable("employeeId")
                                                       @Parameter(name = "employeeId", description = " Employee unique identifier")
                                                       long employeeId) {
        return activityService.getActivitiesByEmployeeId(employeeId);
    }

    @Operation(summary = "Create activity")
    @PostMapping("/activity")
    public ActivityDto createActivity(@Parameter(name = "activityDto", description = "DTO for creating activity")
                                      @RequestBody @Valid SaveActivityDto activityDto) {
        return activityService.createActivity(activityDto);
    }

    @Operation(summary = "Update activity")
    @PutMapping("/activity")
    public ActivityDto updateActivity(@Parameter(name = "activityDto", description = "DTO for updating activity")
                                      @RequestBody @Valid ActivityDto activityDto) {
        return activityService.updateActivity(activityDto);
    }

    @Operation(summary = "Create link between activity and employee")
    @PostMapping("/employee_activity")
    public String createEmployeeActivity(@Parameter(name = "createEmployeeActivityDto",
            description = "DTO for creating link between activity and employee")
                                         @RequestBody @Valid CreateEmployeeActivityDto createEmployeeActivityDto) {
        return activityService.createEmployeeActivity(createEmployeeActivityDto);
    }
}
