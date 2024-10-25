package com.timetracker.timetrackerapptesttask.facade;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.exception.*;

public interface IProjectActivityFacade {
    Long createActivity(CreateActivityDto activityDto) throws ProjectNotFoundException;


}
