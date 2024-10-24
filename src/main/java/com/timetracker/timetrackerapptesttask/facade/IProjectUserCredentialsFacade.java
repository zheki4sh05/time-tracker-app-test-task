package com.timetracker.timetrackerapptesttask.facade;

import com.timetracker.timetrackerapptesttask.dto.*;

import java.util.*;

public interface IProjectUserCredentialsFacade {
    Long createNewProject(ProjectDto createProjectDTO);

    List<ProjectDto> getAuthenticatedUserProjects();

    UserDto addUser(AddUserProjectDto email);

    void deleteByAuthenticatedUser(String email);
}
