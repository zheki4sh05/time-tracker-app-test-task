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

@Service
public class IProjectControlImpl implements IProjectControl {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    public Long createNewProject(ProjectDto createProjectDTO, User user) {

        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setDescription(createProjectDTO.getDesc());

        project = projectRepository.save(project);

        Participation participation = new Participation();

        participation.setProject(project);
        participation.setRole(roleRepository.getWithPriority(RoleDto.ADMIN.getPriority()));
        participation.setUser(user);

        participationRepository.save(participation);

        return project.getId();
    }

    @Override
    public void deleteProject(Long id) throws EntityNotFoundException {

        Project project =projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        projectRepository.delete(project);

    }

    @Override
    public ProjectDto updateProject(ProjectDto projectUpdateDto) {



        return null;
    }

    @Override
    public List<ProjectDto> getAllProjects(Long userId) {

        var dtos = new ArrayList<ProjectDto>();

        List<Participation> participations = participationRepository.findAllByUserId(userId);

        participations.forEach(item->{
            dtos.add(mapFromEntityToDto(item.getProject(), item.getRole()));
        });

        return dtos;
    }

    private ProjectDto mapFromEntityToDto(Project project, Role role) {



      return ProjectDto.builder()
              .id(project.getId())
              .name(project.getName())
              .desc(project.getDescription())
              .role(role.getName())
              .rolePriority(role.getPriority())
              .build();


    }

    @Override
    public List<UserRoleInProjectDto> getAllWorkers(Long projectId) {

        List<Participation> participations = participationRepository.getWorkersBy(projectId);

        var dtos = new ArrayList<UserRoleInProjectDto>();

        participations.forEach(item->{
            dtos.add(UserRoleInProjectDto.builder()
                            .email(item.getUser().getEmail())
                            .name(item.getUser().getName())
                            .lastname(item.getUser().getLastname())
                            .priority(item.getRole().getPriority())
                    .build());
        });


        return dtos;
    }

    @Override
    public Participation createNewParticipation(AddUserProjectDto addUserProjectDto, Long userId) throws UserIsNotAdminException {

        Participation participation = participationRepository.getByUserId(userId, addUserProjectDto.getProjectId(),1).orElseThrow(UserIsNotAdminException::new);
        Role role = roleRepository.getWithPriority(RoleDto.USER.getPriority());

        return Participation.builder()
                .project(participation.getProject())
                .role(role)
                .build();
    }

    @Override
    public void deleteUser(Long deleteUserId, Long userId, Long projectId) {
        Participation participation = participationRepository.getByUserId(userId, projectId,1).orElseThrow(UserIsNotAdminException::new);
       Participation participation1 =  participationRepository.getByUserId(deleteUserId,participation.getProject().getId(), 2).orElseThrow(EntityNotFoundException::new);
        participationRepository.delete(participation1);

    }

    @Override
    public Project findById(Long projectId, Long id) throws ProjectNotFoundException {

        Participation participation = participationRepository.findByProjectAndUserId(projectId,id);

        if(participation==null)
            throw new ProjectNotFoundException();

        return participation.getProject();
    }
}
