package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.entity.Activity;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
public class IActivityControlImpl implements IActivityControl {

    @Autowired
    private ActivityRepository repository;

    @Override
    public Long createActivity(String name, Project project) {

        Activity activity = Activity.builder()
                .name(name)
                .project(project)
                .build();



        return repository.save(activity).getId();
    }

    @Override
    public String startActivity(ActivityDto activityDto) {

        Activity Activity = repository.findById(activityDto.getId(), activityDto.getProjectId()).orElseThrow(ActivityNotFoundException::new);

        Activity.setStarted(Timestamp.valueOf(LocalDateTime.now()));

        repository.save(Activity);

        return Activity.getStarted().toString();
    }

    @Override
    public String finishActivity(ActivityDto activityDto) {

        Activity activity = repository.findById(activityDto.getId(), activityDto.getProjectId()).orElseThrow(ActivityNotFoundException::new);

        activity.setFinished(Timestamp.valueOf(LocalDateTime.now()));

        repository.save(activity);

        return activity.getFinished().toString();
    }

    @Override
    public void deleteActivity(Long id) {

        repository.delete(repository.findById(id).orElseThrow(ActivityNotFoundException::new));

    }

    @Override
    public List<ActivityDto> getAllByProject(Long projectId) {

        List<Activity> activityList = repository.getAllByProject(projectId).orElseThrow(ProjectNotFoundException::new);

        var activityDtoList = new ArrayList<ActivityDto>();

        activityList.forEach(item->{
            activityDtoList.add(ActivityDto.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .startAt(item.getStarted()!=null ? item.getStarted().toString() : null)
                            .finishedAt(item.getFinished()!=null ? item.getFinished().toString() : null)
                            .projectId(item.getProject().getId())
                    .build());
        });

        return activityDtoList;
    }
}
