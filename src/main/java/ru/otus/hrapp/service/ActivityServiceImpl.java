package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ActivityDto;
import ru.otus.hrapp.model.dto.CreateEmployeeActivityDto;
import ru.otus.hrapp.model.dto.SaveActivityDto;
import ru.otus.hrapp.model.entity.Activity;
import ru.otus.hrapp.model.entity.EmployeeActivity;
import ru.otus.hrapp.model.entity.EmployeeActivityID;
import ru.otus.hrapp.model.enumeration.ActivityStatus;
import ru.otus.hrapp.repository.ActivityRepository;
import ru.otus.hrapp.repository.EmployeeActivityRepository;
import ru.otus.hrapp.repository.mapper.UpdateMapper;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final EmployeeActivityRepository employeeActivityRepository;
    private final UpdateMapper updateMapper;

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public List<ActivityDto> getAllActivities() {
        log.info("getAllActivities method was called");

        return activityRepository.findAll().stream()
                .map(ModelConverter::toActivityDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public ActivityDto getActivityById(long activityId) {
        log.info("getActivityById method was called with activityId : {}", activityId);

        return activityRepository.findById(activityId).map(ModelConverter::toActivityDto)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + activityId));
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'HR_MANAGER')")
    public List<ActivityDto> getActivitiesByEmployeeId(long employeeId) {
        log.info("getActivitiesByEmployeeId method was called with employeeId : {}", employeeId);

        return activityRepository.findActivitiesByEmployeeId(employeeId).stream()
                .map(ModelConverter::toActivityDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public ActivityDto createActivity(SaveActivityDto activityDto) {
        log.info("createActivity method was called with activityDto : {}", activityDto);

        var activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setDescription(activityDto.getDescription());
        activity.setStatus(ActivityStatus.PLANNED);
        activity.setStartDate(activityDto.getStartDate());
        activity.setEndDate(activityDto.getEndDate());

        return ModelConverter.toActivityDto(activityRepository.save(activity));
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public ActivityDto updateActivity(ActivityDto activityDto) {
        log.debug("updateActivity method was called with activityId: " + activityDto.getId());

        Optional<Activity> activityOptional = activityRepository.findById(activityDto.getId());
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            updateMapper.updateActivity(activityDto, activity);
            return ModelConverter.toActivityDto(activityRepository.save(activity));
        } else {
            throw new ResourceNotFoundException("No activity is found for the id: " + activityDto.getId());
        }
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public String createEmployeeActivity(CreateEmployeeActivityDto createEmployeeActivityDto) {
        log.debug("createEmployeeActivity method was called with createEmployeeActivityDto: " + createEmployeeActivityDto);

        EmployeeActivity employeeActivity = new EmployeeActivity();
        employeeActivity.setEmployeeActivityID(new EmployeeActivityID(createEmployeeActivityDto.getActivityId(),
                createEmployeeActivityDto.getEmployeeId()));
        employeeActivity.setActivityRole(createEmployeeActivityDto.getActivityRole());

        employeeActivityRepository.save(employeeActivity);

        return "EmployeeActivity successfully created";
    }
}
