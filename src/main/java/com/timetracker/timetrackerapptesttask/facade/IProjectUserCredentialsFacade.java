package com.timetracker.timetrackerapptesttask.facade;

import com.timetracker.timetrackerapptesttask.dto.*;
import jakarta.persistence.*;

import java.util.*;

public interface IProjectUserCredentialsFacade {
    Long createNewProject(ProjectDto createProjectDTO);

    List<ProjectDto> getAuthenticatedUserProjects();

    UserDto addUser(AddUserProjectDto email);

    void deleteByAuthenticatedUser(String email, Long projectId);

    ProjectDto updateProject(ProjectDto projectUpdateDto) throws EntityNotFoundException;
}
