package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.dto.RoleDto;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Сервис для управления проектами пользователей.
 */
@Service
public class IProjectControlImpl implements IProjectControl {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ParticipationRepository participationRepository;


    /**
     * Создает новый проект и возвращает его идентификатор.
     * @param createProjectDTO Данные для создания проекта
     * @param user Пользователь, выполняющий операцию
     * @return Идентификатор созданного проекта
     */
    @Override
    public Long createNewProject(ProjectDto createProjectDTO, User user) {
        // Создаем объект проекта
        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setDescription(createProjectDTO.getDesc());
        // Сохраняем проект в репозитории
        project = projectRepository.save(project);
        // Создаем объект участия
        Participation participation = new Participation();
        // Сохраняем участие в репозитории
        participation.setProject(project);
        participation.setRole(roleRepository.getWithPriority(RoleDto.ADMIN.getPriority()));
        participation.setUser(user);

        participationRepository.save(participation);
        // Возвращаем идентификатор созданного проекта
        return project.getId();
    }

    /**
     * Удаляет проект по его идентификатору.
     * @param id Идентификатор проекта для удаления
     * @throws EntityNotFoundException Если проект с указанным идентификатором не найден
     */
    @Override
    public void deleteProject(Long id) throws EntityNotFoundException {  // Если проект не найден, выбрасываем исключение
// Ищем проект по идентификатору
        Project project =projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
// Удаляем проект из репозитория
        projectRepository.delete(project);

    }
    /**
     * Обновляет данные проекта на основе переданных данных.
     *
     * @param projectUpdateDto Данные для обновления проекта
     * @param userId Идентификатор пользователя, выполняющего операцию
     * @return Обновленные данные проекта в виде DTO
     * @throws EntityNotFoundException Если проект с указанным идентификатором не найден
     */
    @Override
    public ProjectDto updateProject(ProjectDto projectUpdateDto, Long userId) throws EntityNotFoundException {
        // Находим участие пользователя в проекте
        Participation participation = participationRepository.findByProjectAndUserId(projectUpdateDto.getId(),userId);
        // Получаем объект проекта из участия
        Project p = participation.getProject();
        p.setName(projectUpdateDto.getName());
        p.setDescription(projectUpdateDto.getDesc());
        // Возвращаем обновленные данные проекта в виде DTO
        return mapFromEntityToDto(p, participation.getRole());
    }
    /**
     * Возвращает список всех проектов, связанных с указанным пользователем.
     *
     * @param userId Идентификатор пользователя
     * @return Список проектов в виде DTO
     */
    @Override
    public List<ProjectDto> getAllProjects(Long userId) {
    // Создаем список для хранения DTO проектов
        var dtos = new ArrayList<ProjectDto>();
    // Получаем список участий пользователя
        List<Participation> participations = participationRepository.findAllByUserId(userId);
    // Преобразуем проект каждого участия в DTO и добавляем в список
        participations.forEach(item->{
            dtos.add(mapFromEntityToDto(item.getProject(), item.getRole()));
        });

        return dtos;
    }

    /**
     * Мапит объект проекта в объект DTO.
     *
     * @param project Объект проекта
     * @param role Роль пользователя в проекте
     * @return Объект проекта в виде DTO
     */
    private ProjectDto mapFromEntityToDto(Project project, Role role) {

      return ProjectDto.builder()
              .id(project.getId())
              .name(project.getName())
              .desc(project.getDescription())
              .role(role.getName())
              .rolePriority(role.getPriority())
              .build();
    }
    /**
     * Возвращает список всех работников (участников) проекта по его идентификатору.
     *
     * @param projectId Идентификатор проекта
     * @return Список работников проекта в виде DTO
     */
    @Override
    public List<UserRoleInProjectDto> getAllWorkers(Long projectId) {
// Получаем список участий (Participation) для указанного проекта
        List<Participation> participations = participationRepository.getWorkersBy(projectId);
        // Создаем список для хранения DTO работников
        var dtos = new ArrayList<UserRoleInProjectDto>();
        // Преобразуем каждое участие в DTO и добавляем в список
        participations.forEach(item->{
            dtos.add(UserRoleInProjectDto.builder()
                            .email(item.getUser().getEmail())
                            .name(item.getUser().getName())
                            .lastname(item.getUser().getLastname())
                            .priority(item.getRole().getPriority())
                    .build());
        });
        // Возвращаем список работников проекта
        return dtos;
    }


/**
 * Создает новое участие в проекте для пользователя с указанным идентификатором.
 * @param addUserProjectDto Данные о пользователе и проекте, которые необходимо добавить.
 * @param userId Идентификатор пользователя.
 * @return Созданное участие в проекте.
 * @throws UserIsNotAdminException Если пользователь не является администратором.
 */
    @Override
    public Participation createNewParticipation(AddUserProjectDto addUserProjectDto, Long userId) throws UserIsNotAdminException {
        // Получаем существующее участие пользователя в проекте
        Participation participation = participationRepository.getByUserId(userId, addUserProjectDto.getProjectId(),1).orElseThrow(UserIsNotAdminException::new);
        // Получаем роль с приоритетом "USER"
        Role role = roleRepository.getWithPriority(RoleDto.USER.getPriority());
// Создаем новое участие с указанной ролью и проектом
        return Participation.builder()
                .project(participation.getProject())
                .role(role)
                .build();
    }

    /**
     * Удаляет пользователя из участия в проекте.
     * @param deleteUserId Идентификатор пользователя, которого необходимо удалить.
     * @param userId Идентификатор текущего пользователя (администратора).
     * @param projectId Идентификатор проекта.
     * @throws UserIsNotAdminException Если текущий пользователь не является администратором.
     * @throws EntityNotFoundException Если не найдено участие пользователя в указанном проекте.
     */
    @Override
    public void deleteUser(Long deleteUserId, Long userId, Long projectId) {
        // Получаем участие текущего(аутентифицированного) пользователя в проекте
        Participation participation = participationRepository.getByUserId(userId, projectId,1).orElseThrow(UserIsNotAdminException::new);
        // Получаем участие пользователя, которого нужно удалить
       Participation participation1 =  participationRepository.getByUserId(deleteUserId,participation.getProject().getId(), 2).orElseThrow(EntityNotFoundException::new);
        // Удаляем участие пользователя из проекта
        participationRepository.delete(participation1);

    }
    /**
     * Находит проект по его идентификатору и идентификатору пользователя.
     * @param projectId Идентификатор проекта.
     * @param id Идентификатор пользователя.
     * @return Найденный проект.
     * @throws ProjectNotFoundException Если проект не найден.
     */
    @Override
    public Project findById(Long projectId, Long id) throws ProjectNotFoundException {
    // Ищем участие пользователя в указанном проекте
        Participation participation = participationRepository.findByProjectAndUserId(projectId,id);
        // Если участие не найдено, выбрасываем исключение
        if(participation==null)
            throw new ProjectNotFoundException();
// Возвращаем найденный проект
        return participation.getProject();
    }
}
