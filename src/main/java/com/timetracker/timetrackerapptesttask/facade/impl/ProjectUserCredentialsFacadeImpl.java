package com.timetracker.timetrackerapptesttask.facade.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class ProjectUserCredentialsFacadeImpl implements IProjectUserCredentialsFacade {

    @Autowired
    private IProjectControl projectControl;

    @Autowired
    private AuthenticatedUserData authenticatedUserData;

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    public Long createNewProject(ProjectDto createProjectDTO) {

        User user = authenticatedUserData.getCurrentUser();

      Long id =   projectControl.createNewProject(createProjectDTO, user);

        return id;
    }

    @Override
    public List<ProjectDto> getAuthenticatedUserProjects() {

        User user = authenticatedUserData.getCurrentUser();

        return projectControl.getAllProjects(user.getId());
    }

    @Override
    public UserDto addUser(AddUserProjectDto projectDto) throws UserIsNotAdminException, UserNotFoundException {

        User user = authenticatedUserData.getCurrentUser();

        Participation participation = projectControl.createNewParticipation(projectDto,user.getId());

        User addUser = authenticatedUserData.getUserByEmail(projectDto.getEmail()).orElseThrow(UserNotFoundException::new);

        participation.setUser(addUser);

        participationRepository.save(participation);

        return authenticatedUserData.mapFrom(addUser);
    }

    @Override
    public void deleteByAuthenticatedUser(String email) {
        User addUser = authenticatedUserData.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
