package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ActivityDto;
import ru.otus.hrapp.model.dto.CreateEmployeeActivityDto;

import java.util.List;

public interface ActivityService {
    ActivityDto createActivity(ActivityDto activityDto);

    ActivityDto getActivityById(long activityId);

    List<ActivityDto> getActivitiesByEmployeeId(long employeeId);

    List<ActivityDto> getAllActivities();

    ActivityDto updateActivity(ActivityDto activityDto);

    String createEmployeeActivity(CreateEmployeeActivityDto createEmployeeActivityDto);
}
