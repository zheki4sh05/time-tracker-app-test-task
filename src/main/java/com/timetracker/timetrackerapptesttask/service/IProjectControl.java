package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.exception.*;

import java.util.*;

public interface IProjectControl {
    Long createNewProject(ProjectDto createProjectDTO, User user);

    void deleteProject(Long id);

    ProjectDto updateProject(ProjectDto projectUpdateDto);

    List<ProjectDto> getAllProjects(Long userId);

    List<UserRoleInProjectDto> getAllWorkers(Long projectId);

    Participation createNewParticipation(AddUserProjectDto addUserProjectDto, Long userId) throws UserIsNotAdminException;

     void deleteUser(Long deleteUserId, Long userId, Long projectId);
}
