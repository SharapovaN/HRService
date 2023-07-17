package ru.otus.hrapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hrapp.model.dto.ActivityDto;
import ru.otus.hrapp.model.dto.CreateEmployeeActivityDto;
import ru.otus.hrapp.model.entity.Activity;
import ru.otus.hrapp.model.entity.EmployeeActivity;
import ru.otus.hrapp.model.entity.EmployeeActivityID;
import ru.otus.hrapp.model.enumeration.ActivityStatus;
import ru.otus.hrapp.model.enumeration.EmployeeActivityRole;
import ru.otus.hrapp.repository.ActivityRepository;
import ru.otus.hrapp.repository.EmployeeActivityRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @InjectMocks
    ActivityServiceImpl activityService;

    @Mock
    ActivityRepository activityRepository;

    @Mock
    EmployeeActivityRepository employeeActivityRepository;

    @Test
    void createActivity() {
        given(activityRepository.save(new Activity(null, "activity", null,
                ActivityStatus.PLANNED, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))))
                .willReturn(new Activity(1L, "activity", null,
                        ActivityStatus.PLANNED, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5)));

        var activityDto = new ActivityDto(null, "activity", null,
                null, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        var createdActivity = activityService.createActivity(activityDto);
        Assertions.assertNotNull(createdActivity);
        Assertions.assertEquals(createdActivity.getId(), 1);
        Assertions.assertEquals(createdActivity.getName(), "activity");
        Assertions.assertEquals(createdActivity.getStatus(), ActivityStatus.PLANNED);
    }

    @Test
    void getActivityByIdIfOk() {
        given(activityRepository.findById(1L)).willReturn(Optional.of(new Activity(1L, "activity", null,
                ActivityStatus.PLANNED, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))));

        var activityDto = activityService.getActivityById(1);
        Assertions.assertNotNull(activityDto);
        Assertions.assertEquals(activityDto.getId(), 1);
        Assertions.assertEquals(activityDto.getName(), "activity");
        Assertions.assertEquals(activityDto.getStatus(), ActivityStatus.PLANNED);
    }

    @Test
    void getActivityByIdIfNotOk() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> activityService.getActivityById(1));
    }

    @Test
    void getActivityByEmployeeIdIfOk() {
        given(activityRepository.findActivitiesByEmployeeId(1L)).willReturn(List.of(new Activity(1L, "activity", null,
                ActivityStatus.PLANNED, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))));

        var activityDtos = activityService.getActivitiesByEmployeeId(1);
        Assertions.assertNotNull(activityDtos);
        Assertions.assertEquals(activityDtos.get(0).getId(), 1);
        Assertions.assertEquals(activityDtos.get(0).getName(), "activity");
        Assertions.assertEquals(activityDtos.get(0).getStatus(), ActivityStatus.PLANNED);
    }

    @Test
    void getActivityByEmployeeIdIfNotOk() {
        given(activityRepository.findActivitiesByEmployeeId(1L)).willReturn(new ArrayList<>());
        Assertions.assertTrue(activityService.getActivitiesByEmployeeId(1).isEmpty());
    }

    @Test
    void getAllActivitiesIfOk() {
        given(activityRepository.findAll()).willReturn(List.of(new Activity(1L, "activity", null,
                ActivityStatus.PLANNED, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))));

        var activityDtos = activityService.getAllActivities();
        Assertions.assertNotNull(activityDtos);
        Assertions.assertEquals(activityDtos.get(0).getId(), 1);
        Assertions.assertEquals(activityDtos.get(0).getName(), "activity");
        Assertions.assertEquals(activityDtos.get(0).getStatus(), ActivityStatus.PLANNED);
    }

    @Test
    void updateActivityIfNotExists() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                activityService.updateActivity(new ActivityDto(1L, "activity", null,
                        null, LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))));
    }

    @Test
    void createEmployeeActivity() {
        var employeeActivity = new EmployeeActivity(new EmployeeActivityID(1L, 1L),
                EmployeeActivityRole.OWNER);
        given(employeeActivityRepository.save(employeeActivity)).willReturn(employeeActivity);

        String response = activityService.createEmployeeActivity(new CreateEmployeeActivityDto(1L, 1L, EmployeeActivityRole.OWNER));
        Assertions.assertEquals("EmployeeActivity successfully created", response);
    }
}