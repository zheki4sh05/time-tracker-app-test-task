package com.timetracker.timetrackerapptesttask.facade.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.*;
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

    /**
     * Создает новый проект на основе данных из объекта ProjectDto.
     *
     * @param createProjectDTO Объект, содержащий информацию о новом проекте.
     * @return Идентификатор созданного проекта.
     */
    @Override
    public Long createNewProject(ProjectDto createProjectDTO) {
        // Получаем текущего пользователя
        User user = authenticatedUserData.getCurrentUser();
        // Создаем проект с использованием данных из createProjectDTO и пользователя user
      Long id =   projectControl.createNewProject(createProjectDTO, user);
// Возвращаем идентификатор созданного проекта
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
    public void deleteByAuthenticatedUser(String email, Long projectId) {
        User addUser = authenticatedUserData.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
        User user = authenticatedUserData.getCurrentUser();
       projectControl.deleteUser(addUser.getId(), user.getId(), projectId);


    }

    @Override
    public ProjectDto updateProject(ProjectDto projectUpdateDto) throws EntityNotFoundException {
        User user = authenticatedUserData.getCurrentUser();
        return projectControl.updateProject(projectUpdateDto, user.getId());
    }
}
