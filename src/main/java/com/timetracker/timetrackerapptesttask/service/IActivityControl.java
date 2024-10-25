package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;

import java.util.*;

public interface IActivityControl {
    Long createActivity(String name, Project project);

    String startActivity(ActivityDto activityDto);

    String finishActivity(ActivityDto activityDto);

    void deleteActivity(Long id);

    List<ActivityDto> getAllByProject(Long id);
}
