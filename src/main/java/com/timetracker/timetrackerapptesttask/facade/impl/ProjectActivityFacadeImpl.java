package com.timetracker.timetrackerapptesttask.facade.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ProjectActivityFacadeImpl implements IProjectActivityFacade {

    @Autowired
    private IProjectControl projectControl;

    @Autowired
    private IActivityControl activityControl;

    @Autowired
    private AuthenticatedUserData authenticatedUserData;

    @Override
    public Long createActivity(CreateActivityDto activityDto) throws ProjectNotFoundException {

        User user = authenticatedUserData.getCurrentUser();

        Project project = projectControl.findById(activityDto.getProjectId(),user.getId());

        return activityControl.createActivity(activityDto.getName(), project);
    }


}
