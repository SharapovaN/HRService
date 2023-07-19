package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ActivityDto;
import ru.otus.hrapp.model.dto.CreateEmployeeActivityDto;
import ru.otus.hrapp.model.dto.SaveActivityDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();

    ActivityDto getActivityById(long activityId);

    List<ActivityDto> getActivitiesByEmployeeId(long employeeId);

    ActivityDto createActivity(SaveActivityDto activityDto);

    ActivityDto updateActivity(ActivityDto activityDto);

    String createEmployeeActivity(CreateEmployeeActivityDto createEmployeeActivityDto);
}
